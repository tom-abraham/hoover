
1. Build Details:
    Used maven and docker commands to build image and run
    Note: commands should be run from the base directory where Dockerfile is present.
        Build source code:     mvn clean install
        Build docker image:    docker build -t hover_service_docker.jar .
        Run Docker image:      docker run -p 8080:8080 hover_service_docker.jar
2. Testing : 
   Used Postman  for testing the APIs. Including the curl commands, sample request and response here for further testing.
3. Source Code Details:
   Source code is structured into controller, model and service based on the functionality.
4. Approach:
    Followed Test Driven Development since it helps to find out most of the bugs in development time.
    Tried to capture most of the edge cases while writing tests.

5. Hoover Service Details:
    a) Assumptions:
    1. When the hover hits the wall it remains there idle until it gets a proper instruction which can take it out from there.
    2. Room size should be 0 to 10000 and instruction size is 0 to 4096 chars long to ensure service availability and avoid memory issues.
    3. The service url as "/hoover/clean" and port as 8080
    4. All exceptions logged using slf4j.
    5. starting point is also cleaned since hoover is always on.
   
    b) Sample request with success case:
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
    a) Input parameters missing:
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
    
    b) Room size should not be less than 0:
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
    
    c) Any other exception:
    Response:
     Http response status: '500 INTERNAL_SERVER_ERROR'
     Response body with a specific error message.