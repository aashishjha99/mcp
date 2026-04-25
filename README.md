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

## 🏃 Running the Application

### 1. Docker Compose (Full Stack)

```bash
export OPENAI_API_KEY=your_key
docker-compose up -d --build
```

- **App**: http://localhost:8080
- **Grafana**: http://localhost:3000 (Admin/Admin)
- **Jaeger UI**: http://localhost:16686
- **Prometheus**: http://localhost:9090

### 2. Kubernetes (Minikube)

1. **Start Minikube**: `minikube start`
2. **Set Secret**: `kubectl create secret generic openai-secret --from-literal=OPENAI_API_KEY=your_key`
3. **Deploy**: `kubectl apply -f k8s-deployment.yaml`
4. **Access Services**:
   ```bash
   minikube service spring-app --url
   minikube service grafana --url
   minikube service jaeger --url
   ```

## 📡 API Endpoints

- `POST /rag/chat`: Chat with HR Policy context (requires `username` header).
- `GET /api/memory`: Retrieve chat history.
- `GET /actuator/prometheus`: Raw metrics endpoint.

## 📄 License

MIT License
