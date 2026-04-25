param (
    [Parameter(Mandatory=$false)]
    [ValidateSet("Initial", "Update")]
    [string]$Mode = "Update"
)

# Function to check if a secret exists
function Check-Secret($name) {
    $secret = kubectl get secret $name --ignore-not-found
    return $null -ne $secret
}

Write-Host "--- Starting Deployment in Mode: $Mode ---" -ForegroundColor Cyan

# Step 1: Ensure Minikube is running and connected
if ($Mode -eq "Initial") {
    $status = minikube status --format '{{.Host}}'
    if ($status -ne "Running") {
        Write-Host "Minikube is not running. Starting Minikube..." -ForegroundColor Yellow
        minikube start --cpus 4 --memory 4096
    }
}

Write-Host "Connecting to Minikube's Docker environment..."
& minikube -p minikube docker-env | Invoke-Expression

# Step 2: Handle Secrets (Initial Mode only)
if ($Mode -eq "Initial") {
    if (-not (Check-Secret "openai-secret")) {
        $apiKey = Read-Host "Enter your OPENAI_API_KEY"
        if ($null -ne $apiKey -and $apiKey -ne "") {
            kubectl create secret generic openai-secret --from-literal=OPENAI_API_KEY=$apiKey
            Write-Host "Secret 'openai-secret' created." -ForegroundColor Green
        } else {
            Write-Error "API Key is required for initial setup."
            return
        }
    } else {
        Write-Host "Secret 'openai-secret' already exists." -ForegroundColor Gray
    }
}

# Step 3: Build the application image
Write-Host "Building new Docker image: spring-ai-app:latest..." -ForegroundColor Cyan
docker build -t spring-ai-app:latest .

# Step 4: Apply Manifests
Write-Host "Applying Kubernetes manifests..."
kubectl apply -f k8s-deployment.yaml

# Step 5: Rollout targeted deployments
if ($Mode -eq "Update") {
    Write-Host "Redeploying App and Qdrant only..." -ForegroundColor Yellow
    kubectl rollout restart deployment qdrant
    kubectl rollout restart deployment spring-app
} else {
    Write-Host "Initial deployment complete. All services are starting up." -ForegroundColor Green
}

# Step 6: Final check
Write-Host "Current status of your deployments:" -ForegroundColor Cyan
kubectl get deployments

Write-Host "`nTo access services, use:" -ForegroundColor Green
Write-Host "minikube service spring-app --url"
Write-Host "minikube service grafana --url"
