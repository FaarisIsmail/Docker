Google Kubernetes Engine Steps:

1. Download k8s files onto google cloud platform (I used git clone)
2. Create Kubernetes deployments for yaml files
	2a. kubectl apply -f sa-frontend-deployment.yaml
	2b. kubectl apply -f sa-logic-deployment.yaml --record
	2c. kubectl apply -f service-sa-logic.yaml --record
	2d. kubectl apply -f sa-web-app-deployment.yaml --record
	2e. kubectl apply -f sa-web-app-deployment.yaml
3. Expose deployment for sa-frontend and sa-web-app using LoadBalancer Type
	3a. kubectl expose deployment sa-frontend --type=LoadBalancer --name=sa-frontend-lb
	3a. kubectl expose deployment sa-webapp --type=LoadBalancer --name=sa-webapp-lb
4. Run "minikube service list" to get sa-frontend URL
5. Change "analyze sequence URL in sa-frontend/src/App.js to above URL. Append + /sentiment
6. Run yarn build in sa-frontend directory
7. Build and Push docker container for sa-frontend
8. Edit sa-frontend-deployment.yaml for new image
9. Run kubectl apply -f sa-frontend-deployment.yaml
10. Run minikube service sa-frontend-lb