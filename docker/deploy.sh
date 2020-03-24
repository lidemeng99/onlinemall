#!/bin/bash
#
 cd ././../product/
 gradle build -x test
 cd ././../sales/
 gradle build -x test
 cd ../docker
sudo docker-compose down
sudo docker-compose build
sudo docker image prune -f
sudo docker-compose up -d