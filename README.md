# Nearby Earthquakes

Retrieve the top 10 nearest earthquakes based on calculated distance.

## Build

```sh
$ ./gradlew clean build
```

## Tests

```sh
$ ./gradlew test
```

## Run

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

## Docker

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