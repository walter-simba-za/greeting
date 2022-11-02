#Greetings service

## Create docker image

- mvn spring-boot:build-image -DskipTests=true
- docker images

## Run docker image 

- docker run -p 8080:8080 zwibvafhi/greeting-service

## Create self-signed certificate

- keytool -genkey -alias greetings -keyalg RSA -keysize 2048 -validity 3650 -keypass changeit -storepass changeit -keystore server.jks

## Create k8s cluster using kind

- kind create cluster --config k8s/kind-cluster/config.yaml
- kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0-beta8/aio/deploy/recommended.yaml
- kubectl get nodes -o wide
- kubectl apply -f k8s/kind-cluster/dashboard-admin-user.yaml
- kubectl apply -f k8s/kind-cluster/cluster-role-binding.yaml
- kubectl apply -f k8s/kind-cluster/admin-user-secret.yaml
- kubectl proxy
- kubectl -n kubernetes-dashboard describe secret $(kubectl -n kubernetes-dashboard get secret | grep admin-user | awk '{print $1}')
- kind load docker-image zwibvafhi/greeting-service:0.0.1-SNAPSHOT --name zwibvafhi-dev
- docker exec -it zwibvafhi-dev-control-plane crictl images
- docker exec -it zwibvafhi-dev-control-plane crictl rmi docker.io/zwibvafhi/greeting-service
- kind delete cluster --name=zwibvafhi-dev

## Install kubeval to validate configuration files

- brew tap instrumenta/instrumenta
- brew install kubeva

## Deploy app to kubernetes using kubectl

- kubectl create namespace greeting-dev
- kubectl create deployment greeting-service --image=zwibvafhi/greeting-service:0.0.1-SNAPSHOT --namespace greeting-dev
- kubectl create service nodeport greeting-service --tcp=8080:8080 --namespace greeting-dev

## Deploy app to Kubernetes using configuration files

- kubectl apply -f k8s/application/namespace.yaml
- kubectl apply -f k8s/application/deployment.yaml
- kubectl apply -f k8s/application/service.yaml

## Deploy app using helm chart
- helm install greeting-service --dry-run --debug helm-charts
- helm install greeting-service helm-charts
- helm ls --all
- helm uninstall greeting-service

## Install Argo CD
- See https://github.com/argoproj/argo-cd/releases
- kubectl create namespace argocd
- kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/v2.5.0/manifests/install.yaml
- brew install argocd
- argocd version --client
- kubectl port-forward svc/argocd-server -n argocd 9080:443
- export argocd_password=$(kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d)\n
- argocd login  --insecure --username=admin --password=${argocd_password} localhost:9080\n
- argocd cluster list
- echo $argocd_password

## Install gitea

- helm repo add gitea-charts https://dl.gitea.io/charts/
- helm repo update
- helm repo list
- helm show values gitea-charts/gitea > helm-charts/gitea-values.yaml
- helm install --namespace gitea --create-namespace gitea gitea-charts/gitea -f helm-charts/gitea-values.yaml 
- kubectl --namespace gitea get pod gitea-0 -o json | jq '.status.initContainerStatuses[] | {name, state}'
- kubectl --namespace gitea port-forward svc/gitea-http 3000:3000

## Monitoring the app

- kubectl get events --sort-by=.metadata.creationTimestamp -n greeting-dev
- kubectl get deployments -n greeting-dev
- kubectl get pods -n greeting-dev
- kubectl logs $(kubectl get pod -l app=greeting-service -o name) -n greeting-dev
- kubectl describe pod --selector="app=greeting-service" -n greeting-dev
- kubectl get endpoints -n greeting-dev
- kubectl get services -n greeting-dev
- kubectl port-forward svc/greeting-service 8080:8080 -n greeting-dev
- kubectl delete namespace greeting-dev
