# Spring AI Project (MCP)

A comprehensive showcase of **Spring AI** capabilities, implementing advanced AI patterns including RAG (Retrieval-Augmented Generation), Chat Memory, Structured Outputs, and Tool/Function calling.

## 🚀 Features

- **Conversational AI**: Seamless chat interactions powered by OpenAI.
- **RAG (Retrieval-Augmented Generation)**: Intelligent document processing and retrieval using **Qdrant** vector store and **Apache Tika**.
- **Chat Memory**: Persistent conversation history stored in **MySQL** using JDBC Chat Memory repository.
- **Structured Outputs**: Ability to generate structured data (JSON) directly from AI responses.
- **Function Calling / Tools**: Enabling the AI to interact with external systems and custom tools.
- **Containerized Deployment**: Ready for **Docker** and **Kubernetes**.

## 🛠 Tech Stack

- **Java 21**
- **Spring Boot 3.3.5**
- **Spring AI (1.0.0)**
- **OpenAI** (Chat & Embeddings)
- **Qdrant** (Vector Database)
- **MySQL** (Relational Database)
- **Docker & Docker Compose**
- **Kubernetes**

## 📋 Prerequisites

- **Java 21** or higher
- **Docker & Docker Compose**
- **OpenAI API Key**

## ⚙️ Configuration

The application requires the following environment variables:

| Variable | Description | Default |
|----------|-------------|---------|
| `OPENAI_API_KEY` | Your OpenAI API Key | (Required) |
| `DB_HOST` | MySQL Host | `localhost` |
| `DB_NAME` | MySQL Database Name | `spring_ai_db` |
| `SPRING_AI_VECTORSTORE_QDRANT_HOST` | Qdrant Host | `localhost` |

## 🏃 Running the Application

### 1. Local Development (with Docker Compose)

Spring Boot will automatically start the required services (MySQL, Qdrant) using the `docker-compose.yml` file.

```bash
export OPENAI_API_KEY=your_api_key_here
./gradlew bootRun
```

### 2. Docker Compose (Manual)

```bash
docker-compose up -d
```

### 3. Kubernetes Deployment

Apply the Kubernetes manifests:

```bash
kubectl apply -f k8s-deployment.yaml
```

## 📡 API Endpoints

- `GET /api/chat`: Basic chat endpoint.
- `POST /api/rag`: RAG-based query endpoint.
- `GET /api/memory`: Retrieve chat history.
- `GET /api/structured`: Structured output demonstration.
- `GET /api/tools`: Tool calling demonstration.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.
