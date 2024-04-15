FROM azul/zulu-openjdk-alpine:22
LABEL "maintainer" = "gaucimaistre.com"
VOLUME /tmp
WORKDIR /app
EXPOSE 8080

COPY gradle gradle
COPY settings.gradle .
COPY gradlew .
COPY build.gradle .
COPY src src

CMD ["./gradlew", "bootRun"]