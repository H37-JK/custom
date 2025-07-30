#!/bin/bash
# This script is run on the deployment server

# Exit immediately if a command exits with a non-zero status.
set -e

# Docker Hub credentials
DOCKER_USERNAME=$1
DOCKER_TOKEN=$2
IMAGE_NAME=$3
IMAGE_TAG=$4

# Log in to Docker Hub
echo "Logging in to Docker Hub..."
echo "$DOCKER_TOKEN" | docker login -u "$DOCKER_USERNAME" --password-stdin

# Pull the new image
echo "Pulling new image: $IMAGE_NAME:$IMAGE_TAG"
docker pull "$IMAGE_NAME:$IMAGE_TAG"

# Stop and remove the old container if it exists
CONTAINER_NAME="custom-app"
if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    echo "Stopping and removing old container..."
    docker stop $CONTAINER_NAME
    docker rm $CONTAINER_NAME
fi

# Run the new container
echo "Running new container..."
docker run -d --name $CONTAINER_NAME -p 8080:8080 "$IMAGE_NAME:$IMAGE_TAG"

# Clean up old, unused images
echo "Cleaning up old images..."
docker image prune -f

echo "Deployment successful!"
