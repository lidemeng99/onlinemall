#!/bin/bash
#
 cd ././../product/
 ./gradlew build
 cd ././../sales/
 ./gradlew build
 cd ../docker
sudo docker-compose down
sudo docker-compose build
sudo docker image prune -f
sudo docker-compose up -d