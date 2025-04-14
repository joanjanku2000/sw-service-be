# Swisscom Back End App

## Tech Stack
- Java 21
- Spring Boot 3
- MongoDB


The primary goal is to model and manage a 
‘Service‘ entity, which contains a list of
Resource objects. 
Each ‘Resource‘ includes a list of ‘Owner‘ objects.

To run the application Docker must be installed.
## Project Setup
Using Bash Script
``./local_setup.sh``

Stop the containers:   ``docker compose down``

To run the application outside the container through IntellIJ
run only mongo in docker using ``docker compose up mongo_db -d``

The applications default profile is local and will use the pre-configured
mongodb uri matching the host and port to access mongo outside
docker