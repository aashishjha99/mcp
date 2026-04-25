# 🤖 Spring AI & Model Context Protocol (MCP) Showcase

<div align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.3.5-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" />
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/OpenAI-gpt--4o-412991?style=for-the-badge&logo=openai&logoColor=white" />
  <img src="https://img.shields.io/badge/Kubernetes-Enabled-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white" />
</div>

---

## 🌟 Overview

This project is a high-performance implementation of the **Model Context Protocol (MCP)** using the **Spring AI** ecosystem. It demonstrates how to bridge the gap between Large Language Models and enterprise data with a focus on **Observability**, **Security**, and **Scalability**.

By integrating **RAG (Retrieval-Augmented Generation)** with a complete observability stack, this server allows AI agents to interact intelligently with internal documents while providing developers with deep insights into every transaction.

---

## 🚀 Key Features

- 🧠 **Intelligent RAG**: Seamless document processing (PDFs/HR Policies) using **Apache Tika** and **Qdrant** vector store.
- 💬 **Stateful Conversations**: Persistent chat memory managed via **MySQL**.
- 🛠️ **MCP Tool Calling**: Standardized protocol implementation allowing LLMs to execute specific backend tools.
- 📊 **Enterprise Observability**:
    - **Traces**: Distributed tracing with **OpenTelemetry** and **Jaeger**.
    - **Metrics**: Real-time monitoring with **Prometheus** and **Grafana**.
- 🏗️ **Cloud Native**: Ready for deployment via **Docker Compose** or **Kubernetes (Minikube)**.

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.3.5 |
| **AI Orchestration** | Spring AI (1.0.0-M3) |
| **LLM** | OpenAI (GPT-4o) |
| **Vector Database** | Qdrant |
| **Database** | MySQL |
| **Observability** | OpenTelemetry, Jaeger, Prometheus, Grafana |

---

## 🏃 Getting Started

### 📦 Prerequisites
- Java 21+
- Docker & Minikube
- OpenAI API Key

### 🚀 Rapid Deployment (Windows/PowerShell)
We've automated the entire setup. Simply run the `redeploy.ps1` script:

```powershell
# 1. Set execution policy for the session
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope Process

# 2. Launch full stack (Starts Minikube, sets secrets, and deploys)
./redeploy.ps1 -Mode Initial
```

### 🐳 Docker Compose
```bash
export OPENAI_API_KEY=your_key
docker-compose up -d --build
```

---

## 📡 API Interface

- `POST /rag/chat`: Context-aware chat (Pass `username` in header).
- `GET /api/memory`: Fetch conversation history.
- `GET /actuator/prometheus`: Scrape internal metrics.

---

## 🧠 What I Learned (MCP Journey)

Building this project was an deep dive into the future of AI infrastructure:
1. **Standardizing Context**: How MCP acts as the "USB-C" for AI models, allowing them to plug into any data source.
2. **The Power of RAG**: Moving beyond simple prompts to data-driven AI responses.
3. **Observability is Mandatory**: In AI, you need to see *why* a model made a decision—Jaeger and Grafana make that possible.
4. **Spring AI Evolution**: Seeing how Spring brings enterprise patterns (Dependency Injection, Security) to the "Wild West" of AI.

---

## 📄 License
Distributed under the MIT License.

---
<div align="center">
  Made with ❤️ by <a href="https://github.com/aashishjha99">Aashish Jha</a>
</div>
