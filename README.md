# NLP Cloud AI Summarization API 🚀

A lightweight **Java Spring Boot** service that exposes a REST endpoint (`/summarize`) to perform AI-powered text summarization via **NLP Cloud**.  
This project runs in a Docker container and supports quick local or cloud deployment.

---

## 🔧 Features

- POST `/summarize` endpoint: accepts `{"text": "…"}` and returns a summary.
- Built with **Spring Boot 2.6.12** and **Java 8+**.
- Uses **WebClient** to call NLP Cloud’s summarization API.
- Supports Docker builds and `docker-compose` for easy orchestration.
- Environment-based configuration — no hardcoded credentials.

---

## 🧱 Project Structure

```nlp-cloud-summarization-api/
├── pom.xml
└── src/main/java/com/example/
├── Application.java
├── controller/
│ └── SummarizerController.java
└── service/
└── SummaryService.java 
```

---

## ⚙️ Setup & Configuration

1. **Sign up** at [NLP Cloud](https://nlpcloud.com) and get your **API token** (free tier available)
2. Create a `.env` file:
   ```text
   NLPCLOUD_API_TOKEN=your_token_here
   
3. Update application.properties (optional) to change server port:
   ```css
   server.port=8080
   ```

## 🚀 Build & Run Instructions
### Locally (no Docker)

```
mvn clean package
export NLPCLOUD_API_TOKEN=your_token_here
java -jar target/nlp-cloud-summarization-api-1.0-SNAPSHOT.jar
```

### With Docker
```
docker build -t summarizer .
docker run -p 8080:8080 -e NLPCLOUD_API_TOKEN=your_token_here summarizer
```

### With Docker Compose

```
version: "3.8"
services:
summarizer:
build: .
ports:
- "5000:8080"
env_file:
- .env
```

```
docker-compose up --build
```

## 🧪 Test the API
Example curl request:

```
curl -X POST http://localhost:5000/summarize \
-H "Content-Type: application/json" \
-d '{"text":"NLP Cloud offers summarization using models like BART Large CNN"}'
```

### Sample response:

```
{"summary":"NLP Cloud offers summarization using models like BART Large CNN."}
```

## 🧠 How It Works

1. Controller receives the POST /summarize request.
2. SummaryService builds JSON payload and calls NLP Cloud:
   * Adds Authorization: Token <NLPCLOUD_API_TOKEN> header
3. Receives raw response (often text/plain) and parses it with Jackson.
4. Extracts the summary_text field and returns it via JSON.

## 🪄 NLP Cloud Details
* Runs on Meta’s BART Large CNN model or alternatives like T5, LLaMA, Mixtral, etc.
* Free tier available with generous usage limits.
* GDPR, HIPAA compliant; no data stored.

## 🛡️ Configuration & Security Tips
* Do not commit .env or API tokens to Git.
* Rotate your token if compromised.
* For production, use Docker secrets or a secure vault.

