# Solution
## Build and Run
Application is written in Java 8. Can be build by maven  

**mvn package**  

and run

**mvn spring-boot:run**  
or  
**java -jar target/solution-0.1.0.jar**

## Technologies
Application uses Spring Boot and Spring REST services. H2 is used as in-memory database.

## Use cases
Application launches two REST services. The first one is for adding messages and available on endpoint *http://localhost:8080/messages/*.
Another one is for following users and is available on endpoint *http://localhost:8080/follow*.  
Rest endpoints consume HTTP requests and produce responses in JSON format. *curl* command can be used for tests.
Sample curl request are availabe in *sample_curls.sh* script which can be used for generating some data.

### Adding user
User is added when posts their first message. Following curl command adds 'first konrad message' for user konrad.
Message is sent in request body and konrad user in URL path variable.

curl -H "Content-Type: application/json" -X POST -d 'first konrad message' http://localhost:8080/messages/konrad

### Displaing user's messages
Messages added by konrad user can be requested by

curl http://localhost:8080/messages/konrad

Sample response  
[{"content":"first konrad message","author":"konrad","creationDate":1492002711482}]

### Following another user
konrad starts following tom user. konrad is sent in URL path variable and tom in the request body.

curl -H "Content-Type: application/json" -X POST -d 'tom' http://localhost:8080/follow/konrad

### Displaying massages of user followees
curl http://localhost:8080/follow/konrad
