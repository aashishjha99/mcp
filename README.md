# Spring AI Project (MCP)

A comprehensive showcase of **Spring AI** capabilities combined with a robust Observability stack (**Prometheus, Grafana, Jaeger**). This project implements advanced AI patterns including RAG (Retrieval-Augmented Generation), Chat Memory, and Tool calling.

## 🚀 Features

- **Conversational AI**: Powered by OpenAI gpt-4o.
- **RAG (Retrieval-Augmented Generation)**: Intelligent document processing from PDFs (HR Policies) using **Qdrant** and **Apache Tika**.
- **Chat Memory**: Persistent conversation history stored in **MySQL**.
- **Observability**: 
    - **Traces**: Distributed tracing with **OpenTelemetry** and **Jaeger**.
    - **Metrics**: Real-time monitoring with **Micrometer**, **Prometheus**, and **Grafana**.
- **Containerized**: Full **Docker Compose** and **Kubernetes (Minikube)** support.

## 🛠 Tech Stack

- **Java 21 / Spring Boot 3.3.5**
- **Spring AI (1.0.0)**
- **OpenAI** (Chat & Embeddings)
- **Qdrant** (Vector Store)
- **MySQL** (Relational Store)
- **OpenTelemetry** (Instrumentation)
- **Jaeger** (Distributed Tracing)
- **Prometheus** (Metrics Collection)
- **Grafana** (Visualization)

## ⚙️ Configuration

| Variable | Description |
|----------|-------------|
| `OPENAI_API_KEY` | Your OpenAI API Key |
| `SPRING_AI_OTEL_ENDPOINT` | OTLP HTTP Endpoint for traces |

## 🏃 Running the Application (Windows / Minikube)

We provide a specialized PowerShell script `redeploy.ps1` to automate the deployment process.

### 1. Prerequisite: Set Execution Policy
If you haven't run scripts before, allow PowerShell to execute the local script:
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope Process
```

### 2. First-Time Setup
This will start Minikube, prompt you for your OpenAI Key, create the necessary secrets, and deploy the full stack.
```powershell
./redeploy.ps1 -Mode Initial
```

### 3. Redeploying Changes
When you modify your code and want to see changes quickly, run the script in default mode. This only rebuilds and restarts the **App** and **Qdrant** (leaving MySQL/Grafana/Jaeger running).
```powershell
./redeploy.ps1
```

### 4. Accessing Services
After deployment, use these Minikube commands to get the URLs:
```bash
minikube service spring-app --url
minikube service grafana --url
minikube service jaeger --url
```

## 🐳 Running with Docker Compose

```bash
export OPENAI_API_KEY=your_key
docker-compose up -d --build
```

## 📡 API Endpoints

- `POST /rag/chat`: Chat with HR Policy context (requires `username` header).
- `GET /api/memory`: Retrieve chat history.
- `GET /actuator/prometheus`: Raw metrics endpoint.

## 📄 License

MIT License
