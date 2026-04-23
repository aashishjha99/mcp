# Step 1: Make sure the terminal is connected to Minikube's Docker
Write-Host "Connecting to Minikube's Docker environment..."
& minikube -p minikube docker-env | Invoke-Expression

# Step 2: Build a new Docker image with your latest code
Write-Host "Building new Docker image: spring-ai-app:latest..."
docker build -t spring-ai-app:latest .

# Step 3: Apply the Kubernetes manifests (creates/updates deployments and services)
Write-Host "Applying Kubernetes deployments and services..."
kubectl apply -f k8s-deployment.yaml

# Step 4: Force Kubernetes to restart the Qdrant deployment
Write-Host "Rolling out the new Qdrant deployment..."
kubectl rollout restart deployment qdrant

# Step 5: Force Kubernetes to restart the Spring app deployment with the new image
Write-Host "Rolling out the new Spring app deployment..."
kubectl rollout restart deployment spring-app

# Step 6: Watch the new pods come online
Write-Host "Watching for new pods to be ready... (Press Ctrl+C to exit)"
kubectl get pods -l "app=spring-app,app=qdrant" -w
