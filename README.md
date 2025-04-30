# Courier Tracking Service
This project was developed to calculate the store entrance control and the total travel distance of the relevant courier based on the location information sent by a courier.

## Technologies
* Java 21
* Spring Boot 3.4.4
* Spring Data JPA
* PostgreSQL
* Maven
* Lombok
* MapStruct
* Exception Handling
* JUnit & Mockito
* Swagger
* Slf4j

## Prerequisites
* Docker Desktop
* Java 21 or newer

# Installation
1. Clone the repo
```sh
https://github.com/furkanyesilyurt/courier-tracking-service.git
```

2. Run Docker-Compose file
```
 > mvn clean install
```

3. Run Docker-Compose file
```
 > docker-compose up
```

If you need anything related to PostgreSQL PostGIS, check [here](https://postgis.net/documentation/getting_started).
You can reach the Swagger Api-doc.json at [here](https://github.com/furkanyesilyurt/courier-tracking/blob/master/src/main/resources/api-doc.json).