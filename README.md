
1. Build Details:
    used maven and docker commands to build image and run
    Note: commands should be run from the base directory where Dockerfile is present.
        Build source code:     mvn clean install
        Build docker image:    docker build -t hover_service_docker.jar .
        Run Docker image:      docker run -p 8080:8080 hover_service_docker.jar
2. Testing : I have used Postman  for testing the APIs , including the curl commands and sample request and sample response here for further testing.
Source Code Details:
3. Project Structure
    Source code is structured into controller, model and service based on the functionality.
4. Approach
    Followed Test Driven Development since it helps finding out most of the bugs in development time.
    Tried to capture most of the edge cases while writing tests.

5. Hoover Service Details:
    Assumptions:
    1. Hitting wall should send response '400 Bad Request' and  error message "robot has hit the top wall"
    2. Room size can not be less than 0 and all given coordinates should be within room size.
    3. The service url as "/hoover/clean" and port as 8080

    Sample request with success case:
            curl --location 'http://localhost:8080/hoover/clean' \
            --header 'Content-Type: application/json' \
            --data '{
              "roomSize" : [5, 5],
              "coords" : [1, 2],
              "patches" : [
                [1, 0],
                [2, 2],
                [2, 3]
              ],
              "instructions" : "NNESEESWNWW"
            }'

    Response:
     Http response status: '200 OK'
     Response body : {
                         "coords": {
                             "x": 1,
                             "y": 3
                         },
                         "patches": 1
                     }



6. Handling error scenarios:
a) Hit the wall error:
Request:
            curl --location 'http://localhost:8080/hoover/clean' \
            --header 'Content-Type: application/json' \
            --data '{
              "roomSize" : [4, 4],
              "coords" : [1, 2],
              "patches" : [
                [1, 0],
                [2, 2],
                [2, 3]
              ],
              "instructions" : "NNESEESWNWWNNESEESWNWW"
            }'
Response:
 Http response status: '400 Bad Request'
 Response body with a specific error message "robot has hit the top wall"


b) Input parameters missing:
Request:
            curl --location 'http://localhost:8080/hoover/clean' \
            --header 'Content-Type: application/json' \
            --data '{
              "roomSize" : [4, 4],
              "coords" : [1, 2],
              "patches" : [
                [1, 0],
                [2, 2],
                [2, 3]
              ]
            }'
Response:
 Http response status: '400 Bad Request'
 Response body with a specific error message "Input parameter instructions missing"

c) Room size should not be less than 0:
            curl --location 'http://localhost:8080/hoover/clean' \
            --header 'Content-Type: application/json' \
            --data '{
              "roomSize" : [-1, -1],
              "coords" : [1, 2],
              "patches" : [
                [1, 0],
                [2, 2],
                [2, 3]
              ],
              "instructions" : "NNESEESWNWWNNESEESWNWW"
            }'
Response:
 Http response status: '400 Bad Request'
 Response body with a specific error message "Room size should be more than 0"