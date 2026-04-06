# Code Review: Nearby Earthquakes

This document provides a critique of the Nearby Earthquakes take-home project — a Spring Boot REST API that fetches earthquake data from USGS and returns the 10 nearest earthquakes for a given coordinate.

---

## Strengths

- **Clean layered architecture** — controller → service → repository is well-separated and easy to follow.
- **Interface-based client design** — `EarthquakeClient` allows swapping `EarthquakeWebClient` and `EarthquakeFileClient` without touching the service layer. Good use of the strategy pattern.
- **Good use of modern Java** — Records for DTOs, `@RequiredArgsConstructor`, MapStruct for mapping, and Lombok all reduce boilerplate.
- **Profile-based configuration** — `@Profile("!test")` on `RefreshEarthquakesTask` correctly prevents the scheduled fetch from running during tests.
- **Exception handling** — `@ControllerAdvice` with `RestResponseEntityExceptionHandler` provides centralised error handling and meaningful HTTP status codes.
- **Docker support** — A Dockerfile is included.

---

## Issues

### Critical

**1. Latitude/longitude coordinates are swapped in the mapper**

In `EarthquakeEntityMapper`, the coordinates are mapped as:
```java
@Mapping(target = "latitude",  expression = "java(feature.geometry().coordinates().get(0))")
@Mapping(target = "longitude", expression = "java(feature.geometry().coordinates().get(1))")
```

GeoJSON stores coordinates as `[longitude, latitude, elevation]`, so index `0` is longitude and index `1` is latitude. This mapping is reversed, meaning every record in the database has its coordinates swapped. The distance query will return incorrect results for all inputs.

---

**2. `build.gradle` dependency declarations are broken**

The dependencies are incorrectly nested inside the `platform(...)` BOM block:
```groovy
implementation platform("org.springframework.boot:spring-boot-dependencies:3.5.6") {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    // ...
}
```

Dependencies must be declared at the top level of the `dependencies {}` block, not nested inside `platform()`. Only version constraints belong inside the BOM block. This means the application likely does not compile as-is.

---

**3. Tests don't test the actual implementations**

`EarthquakeWebClientTest` and `EarthquakeFileClientTest` both mock the `EarthquakeClient` interface and then just verify that Mockito returns the mocked value. They exercise zero production code. Similarly, `EarthquakeControllerTest.getNearbyEarthquakes()` calls `service.getNearbyEarthquakes(point)` directly rather than `controller.getNearbyEarthquakes(...)`, so the controller is never invoked.

---

### Significant

**4. `@Scheduled` cron expression runs every hour, not daily**

```java
@Scheduled(cron = "0 9 * * * ?")
```

Spring's cron format is `second minute hour day month weekday`. This expression means "at second 0, minute 9, of every hour" — so it fires hourly (e.g., 00:09, 01:09, 02:09, ...). To run once daily at 09:00, it should be:
```java
@Scheduled(cron = "0 0 9 * * ?")
```

---

**5. `saveAll` without clearing causes unbounded database growth**

`RefreshEarthquakesTask.refreshEarthquakes()` fetches the latest data and calls `repository.saveAll()`, but never removes stale records. Over time the database accumulates months of old data. Consider calling `repository.deleteAll()` before saving, or using an upsert strategy with a cleanup of records outside the relevant window.

---

**6. Blocking call inside a reactive `WebClient`**

`EarthquakeWebClient` uses WebFlux's `WebClient` but immediately calls `.block()`, which negates the benefits of reactive I/O and ties up a thread for the duration of the call. If blocking HTTP is acceptable (and it is in a scheduled task), use Spring's `RestClient` or `RestTemplate` instead to make the intent clear and avoid the WebFlux dependency.

---

**7. Dockerfile builds from source on every container start**

```dockerfile
CMD ["./gradlew", "bootRun"]
```

This triggers a full Gradle build — including dependency downloads — every time the container starts. The standard pattern is a multi-stage build: compile a fat JAR in a builder stage, then copy and run it in a minimal JRE image:
```dockerfile
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN ./gradlew bootJar

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

**8. Java version inconsistency**

`build.gradle` and the Dockerfile both reference Java 25 (a pre-release / non-LTS version), while the README states "Java 21 or higher". The project should consistently target a stable LTS release (Java 21).

---

### Minor

**9. Hardcoded placeholder cookie in `WebClientConfig`**

```java
.defaultCookie("cookie-name", "cookie-value")
```

This appears to be leftover from a copied template. The USGS GeoJSON API does not require cookies; this line should be removed.

---

**10. URL logged at INFO level in `EarthquakeWebClient`**

```java
log.info(URL);
```

Logging configuration values at INFO level will appear in production logs on every request. This should be `log.debug(URL)` or removed.

---

**11. No input validation on controller parameters**

`@Validated` is declared on `EarthquakeController`, but `latitude` and `longitude` have no constraint annotations. Callers can pass values like `999.0` or `null` (since `Double` is boxed). Add range constraints:
```java
@RequestParam @NotNull @DecimalMin("-90.0") @DecimalMax("90.0") Double latitude,
@RequestParam @NotNull @DecimalMin("-180.0") @DecimalMax("180.0") Double longitude
```

---

**12. Unused repository methods**

`findByMagnitudeGreaterThanEqual`, `findByMagnitudeLessThanEqual`, and `findByMagnitudeBetween` are declared on `EarthquakeRepository` but never called from any service or controller. Either expose them via the API or remove them.

---

**13. `EarthquakeEntity.distance` is missing `@Column`**

The `distance` field on `EarthquakeEntity` is populated by the native query alias but has no `@Column` annotation, unlike all other fields. While Hibernate may tolerate this, it is inconsistent and could cause issues with schema generation or other JPA providers.

---

**14. README documents the wrong endpoint path**

The README states the endpoint is `/api/nearby`, but the controller maps to `/nearby`. This will mislead anyone following the documentation.

---

**15. `EarthquakeFileClientTest` and `EarthquakeWebClientTest` are duplicates**

Both test files are identical in structure and assertions. Since both mock the same `EarthquakeClient` interface, they provide no differentiated value. The file client test in particular should use `@SpringBootTest` or load an actual fixture to test JSON parsing.

---

**16. `EarthquakeRepositoryTest.findByDistance()` uses hardcoded IDs**

```java
assertThat(actualEarthquakes.get(0).getId()).isEqualTo("us6000mfqs");
```

Asserting specific earthquake IDs ties the test to the contents of the bundled `all_month.geojson` file. If the file is ever updated, the test breaks without any code change. Consider asserting structural properties (list size, distance ordering, non-null fields) rather than specific IDs.

---

**17. `home()` endpoint returns an empty string**

```java
@GetMapping(value = "/")
public String home() {
    return "";
}
```

This endpoint serves no purpose and returns an empty body with a 200 status. It should either be removed or return a meaningful response (e.g., service name/version info).

---

**18. `EarthquakeMapper` missing explicit `componentModel`**

`EarthquakeMapper` and `EarthquakeResponseMapper` rely on the global `mapstruct.defaultComponentModel=spring` compiler argument set in `build.gradle` to become Spring beans, while `EarthquakeEntityMapper` explicitly sets `componentModel = "spring"`. It is better to be explicit in all mappers to make the Spring integration self-evident without requiring knowledge of the build configuration.

---

## Summary

| Category | Count |
|---|---|
| Critical | 3 |
| Significant | 5 |
| Minor | 10 |

The core architecture is sound and shows a good grasp of Spring Boot conventions. The most impactful items to address are the coordinate mapping bug, the broken `build.gradle`, the tests that don't actually test the code they're named after, and the cron expression that fires hourly instead of daily.
