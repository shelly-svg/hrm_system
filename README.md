# Human resources managing system

## Installation and running

### Running the project

Clone this project<br/>
Create an empty database<br/>
Run eureka-server (port:8761)<br/>
Run gateway (port:8090)<br/>
Run auth-service (port:8081)<br/>
Run user-service (port:8082)<br/>
Run staffing-service (port:8083)<br/>

## Technologies stack

* Spring Boot
* Maven
* JPA
* mySQL
* Liquibase

## Services

auth-service will be running on port 8081. Main goal is to head authentification and authorization of system users<br/>
user-service will be running on port 8082. This service will deal with all operations related to users<br/>
staffing-service will be running on port 8083. It will deal with staffing problems such as search/create a positions, open an interviews, etc.<br/>
