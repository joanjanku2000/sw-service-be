#!/bin/bash

# Start Mongo container
docker compose up mongo_db -d
docker compose build app
docker compose run -e SPRING_PROFILES_ACTIVE=local -p 8181:8080 -d app


