# DEMO
[Demo](Link)

# Docker Images
* [Jupyter Notebook](https://hub.docker.com/r/jupyter/minimal-notebook)
* [Apache Spark (Pyspark Notebook)](https://hub.docker.com/r/jupyter/pyspark-notebook)
* [Sonarqube](https://hub.docker.com/_/sonarqube)
* [Sonar Scanner](https://hub.docker.com/r/newtmitch/sonar-scanner)
* [Hadoop Namenode](https://hub.docker.com/layers/bde2020/hadoop-namenode/2.0.0-hadoop3.2.1-java8/images/sha256-51ad9293ec52083c5003ef0aaab00c3dd7d6335ddf495cc1257f97a272cab4c0?context=explore)
* [Hadoop Datanode](https://hub.docker.com/layers/bde2020/hadoop-datanode/2.0.0-hadoop3.2.1-java8/images/sha256-ddf6e9ad55af4f73d2ccb6da31d9e3331ffb94d5f046126db4f40aa348d484bf?context=explore)
* [GUI](Link)

# How to set up application
For simplicity in delploying the microservices to Kubernetes, I have created custom YAML files located the [Deployments](./Deployments) folder. Please download these files onto Google Cloud Platform.

## Assumptions
The following assumptions are made in order to successfully get the applications running

* A working Google Cloud Platform account with availible credits.
* Your Google Cloud Platform has Docker and Kubernets API enabled, as well as Kubernetes Engine
* The YAML files located in the [Deployments](.Deployments) folder are downloaded to Google Cloud Platform (You can use `git clone` on this repository within GCP)
* A running Kubernetes Cluster.

If the user does not have a Kubernetes Cluster created, follow these steps:

1. Navigate to `Kubernets Cllusters` in the Google Cloud Platform
2. Click on `Create`
3. Under `GKE Autopilot` click `Configure`
4. Enter the appropriate cluster name and region
5. Select Public Cluster
6. Click `Creat`" on the bottom
7. Wait for the cluster to set up

## Setting up Jupyter Notebook
1. Navigate to the `Deployments` folder in your Google Cloud Platform terminal containing the YAML files
2. Run the command ```kubectl apply -f jupyter-deployment```

