#!/bin/sh
printf "Starting the DB container..."
docker-compose up -d
printf "Done, Building the app..."
./gradlew build
printf "The app was built, Starting the app..."
./gradlew run
