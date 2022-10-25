
# Trainers API
This project is built using Spring-Boot with Java, maven, JPA, Junit 5. 
It uses liquibase for database versioning and a H2 database in memory.
- Added 2 scripts, one for the database creation, and other one for add some data into it.
- Added an annotation to control api responses time.
- Added error handling.
- Added integration tests.
- Added unit tests.

## Maven Commands
Compile and tests
> mvn clean install

To skip the tests
> mvn clean install -DskipTests

To just run the tests
> mvn test

To run the application
> mvn spring-boot:run

## Database URL:
http://localhost:8080/h2

## Endpoints
There is a postman collection on the root of the project that you can import into postman.

##### Get all trainers
http method: GET
URI: http://localhost:8080/trainers/

##### Get trainer by Id
http method: GET
URI: http://localhost:8080/trainers/{id}

##### Get trainers by name (array)
http method: GET
URI: http://localhost:8080/trainers/name/{name}

##### Save a new trainer
Before saving a new trainer, the app will check the phone number does not exist, otherwise will throw a 409 conflict error.
http method: POST
URI: http://localhost:8080/trainers/  
body:
&nbsp;  {  
&nbsp; &nbsp; &nbsp; &nbsp;  "email": "Alan.Albertengo@gmail.com"  
&nbsp; &nbsp; &nbsp; &nbsp;  "phoneNumber": "1234567890"  
&nbsp; &nbsp; &nbsp; &nbsp;  "firstName": "Alan"  
&nbsp; &nbsp; &nbsp; &nbsp;  "lastName": "Albertengo"  
&nbsp;  }

## Exception Handler
There is an exception handler that will capture every possible exception and will return them in a cleaner way.  

# Next steps
Create endpoints:
- Update existent trainer.
- Delete an existent trainer.

Implement functionality:
- Implement a cache for getting trainers.
- Implement security for adding/modifying/deleting data in the database.
- Put everything on a docker.