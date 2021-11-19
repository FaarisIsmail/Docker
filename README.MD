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
6. Click `Create" on the bottom
7. Wait for the cluster to set up

## Setting up Jupyter Notebook
1. Navigate to the `Deployments` folder in your Google Cloud Platform terminal containing the YAML files
2. Run the command `kubectl apply -f jupyter-deployment.yaml`
3. Run the command `kubectl expose deployment jupyter-notebook --port=8888 --protocol=TCP --target-port=8888 --type=LoadBalancer --name=jupyter-service`
4. Naviagte to `Kubernets Engine -> Services` in GCP to view exposed deployment endpoints

## Setting up Apache Spark (Pyspark Notebook)
1. Navigate to the `Deployments` folder in your Google Cloud Platform terminal containing the YAML files
2. Run the command `kubectl apply -f pyspark-deployment.yaml`
3. Run the command `kubectl expose deployment pyspark-notebook --port=8888 --protocol=TCP --target-port=8888 --type=LoadBalancer --name=pyspark-service`
4. Naviagte to `Kubernets Engine -> Services` in GCP to view exposed deployment endpoints

## Setting up Sonarqube and Sonar Scanner
### Sonarqube
1. Navigate to the `Deployments` folder in your Google Cloud Platform terminal containing the YAML files
2. Run the command `kubectl apply -f sonarqube-deployment.yaml`
3. Run the command `kubectl expose deployment sonarqube --port=9000 --protocol=TCP --target-port=9000 --type=LoadBalancer --name=sonarqube-service`
4. Naviagte to `Kubernets Engine -> Services` in GCP to view exposed deployment endpoints
### Sonarscanner
1. Naviagte to `Kubernets Engine -> Services` in GCP to view exposed deployment endpoints
2. Copy down the `Sonarqube` endpoint IP and port (you will need this later)
3. Click on the `Sonarqube` endpoint IP and port or copy and paste it into your browser URL (it may take a while for Sonarqube to start)
4. Once Sonarqube is started, login with `Username: admin, Password: admin` (you may be prompted to change the password)
5. Once logged in, click on the User icon on the top right (default should be a square with the letter 'A') and click on `My Account`
6. Navigate to the `Security` tab
7. Next you want to Generate a token. Enter a token name (such as scanner) and click `Generate`
8. Copy down the token (you will need this later)
9. Now we need to edit the sonar-scanner-deployment.yaml file. Use a text editor or `nano sonar-scanner-deployment.yaml`
10. At the bottom of the file, you will see `args: ["-Dsonar.host.url=http://${SONARQUBE EXTERNAL IP}", "-Dsonar.login=${SONARQUBE TOKEN}", "-Dsonar.projectName='Sonar Scanner'"]`
11. Replace `${SONARQUBE EXTERNAL IP}` with the Sonarqube IP and Port, and `${SONARQUBE TOKEN}` with the Token you copied down earlier
12. Save the file
13. Run the command `kubectl apply -f sonar-scanner-deployment.yaml`
14. Once the deployment is running, navigate to your Sonarqube endpoint ip again. You should see a new project name "Sonar Scanner" in the projects tab with a completed run of the scan
