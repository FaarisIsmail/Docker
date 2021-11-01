# Docker Image URLS

* Driver - https://hub.docker.com/repository/docker/faarisismail/driver
* Apache Spark - https://hub.docker.com/r/cloudsuite/spark
* Apache Hadoop - https://hub.docker.com/r/harisekhon/hadoop
* Jupyter Notebook - https://hub.docker.com/r/jupyter/base-notebook
* Sonar Scanner - https://hub.docker.com/r/newtmitch/sonar-scanner
* Sonar Qube - https://hub.docker.com/_/sonarqube

# Source Code
[Main Application Terminal](/driver/driver.py)
[Driver Docker file](/driver/Dockerfile)

# Steps to run images on Kubernetes

1. Pull all docker images to GCP
   (eg. `docker pull [DOCKER_IMAGE]`)
2. Tag all images to Google Cloud Registry path
    (eg. `docker tag [DOCKER_IMAGE] gcr.io/[PROJECT_ID]/[USERNAME]/[DOCKER_IMAGE]`)
3. Push images to Google Cloud Registery using tag name
    (eg. `docker push gcr.io/[PROJECT_ID]/[USERNAME]/[DOCKER_IMAGE]`)
4. Navigate to 'Kubernetes Engine' in GCP Console
5. Create a new GKE cluster by clicking 'Create'
6. After creating the GKE cluster, start the deployment by clicking 'Deploy'
7. Under 'Edit Container', select the image path of the Docker Images in the Container Registry
   (You want to do this for all 5 containers. Click 'Add Container' after selecting each image path to add a new container)
8. Hit 'Continue' to be brought to the configuration page
9. Hit 'Deploy' to deploy your images to Kubernetes Cluster
