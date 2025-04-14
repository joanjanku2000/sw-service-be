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

Back-end project APIs are exposed in 
`localhost:8181`

Stop the containers:   ``docker compose down``

To run the application outside the container through IntellIJ
run only mongo in docker using ``docker compose up mongo_db -d``


Inside the repo can be found also a "be_script.sh" which can be
used as a standalone script (without cloning the repo yourself)
and is used in Hostinger Ubuntu Host to trigger the deployment of the back-end services.

The script does everything itself as follows:

- Checks for the repository folder in the folder it is run, if found deletes it - to ensure code is uddated
- clones the repository and cd inside it
- checks for docker and docker compose , and installs them if they are not present
- runs docker compose up --build which setsup mongo, redis, back-end 


Github Actions is used to trigger a workflow, which is defined inside  `.github/workflows/deploy/yml`.
The workflow is triggered on each push and using the HOST, USER and SSH_KEY inside
the configured VM (they are stored as secrets in github secrets section), it executes
the script.sh which is found in the host machine.

Through this step, with each commit we get a mini simulated simple version of CI/CD.



## Logging
Back End Application logs are written in a file inside the repo in the filesystem (but not pushed in git)
They can be accessed by running the following <br>

<pre>
docker exec -it sw-service-app sh
tail -f logs/app-service.log
</pre>
Note: The application should've been started using `docker compose up --build`

## Production Deployment
Project is deployed and can be accessed through the URL:
``http://62.72.33.67``.
Each push , activates the Github workflow, which
triggers the be_setup.sh script in the virtual machine to get the new code
, run the tests , re-build and re-start the containers.

Thank you :)