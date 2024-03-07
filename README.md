# Nearby Earthquakes

Retrieve the top 10 nearest earthquakes based on calculated distance.

## Run
```
$ ./gradlew bootRun
$ curl -v http://127.0.0.1:8080/nearby?latitude=48.193889&longitude=11.221226

{"earthquakes":[{"magnitude":4.8,"address":"185 km NNW of Las Khorey, Somalia","distance":138},{"magnitude":4.0,"address":"21 km NNE of Loyada, Djibouti","distance":541},{"magnitude":4.3,"address":"21 km NNE of Djibouti, Djibouti","distance":557},{"magnitude":4.5,"address":"196 km W of Ad Darb, Saudi Arabia","distance":995},{"magnitude":5.0,"address":"Owen Fracture Zone region","distance":1050},{"magnitude":5.9,"address":"Owen Fracture Zone region","distance":1053},{"magnitude":4.9,"address":"Owen Fracture Zone region","distance":1068},{"magnitude":4.6,"address":"33 km W of Bandar Abbas, Iran","distance":1382},{"magnitude":4.5,"address":"Carlsberg Ridge","distance":1392},{"magnitude":5.0,"address":"56 km NNW of Bandar Abbas, Iran","distance":1421}]}
```