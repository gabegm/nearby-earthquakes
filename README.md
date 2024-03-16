# Earthquake API Service

The Earthquake API Service is a Spring Boot application that provides a RESTful API for retrieving information about earthquakes based on geographical coordinates. It leverages an in-memory cache to store earthquake data and offers endpoints for querying the nearest earthquakes.

## Features

* Retrieve the nearest 10 earthquakes based on longitude and latitude.
* Scheduled task to fetch the latest earthquakes from the U.S. Geological Survey (USGS) daily.

## Installation

### Prerequisites

* Java 21 or higher
* Gradle (if running directly)
* Docker (if using Docker)

## Building the Application

```sh
$ gradle wrapper
$ ./gradlew clean build
```

## Testing the Application

```sh
$ ./gradlew test
```

## Running the Application

1. Directly with Gradle:

```sh
$ ./gradlew bootRun
$ curl -v http://127.0.0.1:8080/nearby?latitude=48.193889&longitude=11.221226

{
    "earthquakes": [
        {
            "title": "M 4.8 | 185 km NNW of Las Khorey, Somalia || 138"
        },
        {
            "title": "M 4.3 | 21 km NNE of Djibouti, Djibouti || 557"
        },
        {
            "title": "M 4.5 | 189 km WNW of Farasān, Saudi Arabia || 986"
        },
        {
            "title": "M 5.0 | Owen Fracture Zone region || 1050"
        },
        {
            "title": "M 5.9 | Owen Fracture Zone region || 1053"
        },
        {
            "title": "M 4.9 | Owen Fracture Zone region || 1068"
        },
        {
            "title": "M 4.6 | 33 km W of Bandar Abbas, Iran || 1382"
        },
        {
            "title": "M 4.5 | Carlsberg Ridge || 1392"
        },
        {
            "title": "M 5.0 | 56 km NNW of Bandar Abbas, Iran || 1421"
        },
        {
            "title": "M 4.6 | Carlsberg Ridge || 1432"
        }
    ]
}
```

2. Using Docker:

```sh
$ docker build -t nearby-earthquakes .
$ docker run -p 8080:8080 -t nearby-earthquakes

$ curl -v http://127.0.0.1:8080/nearby?latitude=48.193889&longitude=11.221226

{
    "earthquakes": [
        ...
    ]
}
```

## API Endpoints

### Get Nearest Earthquakes
* Endpoint: /api/nearby
* Method: GET
* Parameters:
    * longitude: Longitude of the location (required)
    * latitude: Latitude of the location (required)
* Response:
    * Returns the 10 nearest earthquakes in JSON format.

## Configuration

* The application uses an in-memory cache to store earthquake data.
* You can adjust cache settings in application.properties.

## License

This project is licensed under the Apache License - see the LICENSE file for details.
