docker-compose down

# delete all images
docker rmi -f $(docker images -q)

# stop and delete all containers
docker stop $(docker ps -aq)
docker rm $(docker ps -aq)