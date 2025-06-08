# 🚀 WeShorten

## 🇺🇸 English Documentation

A simple, production-ready URL shortener built with Java, Spring Boot, and PostgreSQL. Features a RESTful API, click tracking, access metrics (IP, user agent, timestamp), Docker support, and Traefik integration.

---

### ✨ Features

- 🔗 Shorten URLs with unique 6-character codes
- ↪️ Redirect to original URLs
- 📊 Track clicks and access logs (IP, user agent, timestamp)
- 🛠️ RESTful API for management
- 🐳 Dockerized for easy deployment
- 🚦 Traefik integration for routing and reverse proxy

---

### 🛠️ Tech Stack

- ☕ Java 21
- 🌱 Spring Boot 3
- 🐘 PostgreSQL
- 🐳 Docker & Docker Compose
- 🚦 Traefik (reverse proxy)
- 🗃️ JPA/Hibernate
- 🦾 Lombok
- ⚙️ Gradle

---

### 🚩 Getting Started

#### Prerequisites
- Docker & Docker Compose
- Java 21 (for local development)
- Gradle

#### Running with Docker
1. **Clone the repository:**
   ```sh
   git clone https://github.com/fsousac/weshorten.git
   cd weshorten
   ```
2. **Start the stack:**
   ```sh
   docker compose up --build
   ```
3. **Access the application:**
   - API: [http://short.local/api](http://short.local/api)
   - Shortened links: [http://short.local/{shortCode}](http://short.local/{shortCode})
   - Note: Add `127.0.0.1 short.local` to your `/etc/hosts` for local testing.

---

### 📚 API Endpoints

#### 🔸 Shorten a URL
- **POST** `/api/shorten`
- **Body:** `{ "originalUrl": "https://example.com" }`
- **Response:**
  ```json
  {
    "shortenedUrl": "https://short.local/abc123",
    "originalUrl": "https://example.com"
  }
  ```

#### 🔸 List all shortened URLs
- **GET** `/api/links`
- **Response:**
  ```json
  [
    {
      "shortenedUrl": "https://short.local/abc123",
      "originalUrl": "https://example.com",
      "clicks": 5
    }
  ]
  ```

#### 🔸 Delete a shortened URL
- **DELETE** `/api/links/{shortCode}`

#### 🔸 Redirect
- **GET** `/{shortCode}`
- Redirects to the original URL and logs access metrics.

---

### 📈 Collected Metrics

On each redirect, the following are stored:
- Short code
- IP address
- User agent
- Access timestamp (UTC)

---

### ⚙️ Configuration

- **Profiles:**
  - `dev`: Local development (`application-dev.properties`)
  - `prod`: Production (`application-prod.properties`)
- **Database:**
  - PostgreSQL (see `.env` for credentials)

---

### 👨‍💻 Development

#### Running locally
1. Start PostgreSQL locally or via Docker.
2. Set the active profile to dev:
   ```sh
   export SPRING_PROFILES_ACTIVE=dev
   ```
3. Build and run:
   ```sh
   ./gradlew bootRun
   ```

#### Tests
Run:
```sh
./gradlew test
```

---

### 📄 License

Distributed under [GNU GPL v3](LICENSE).

---

### 🙋 Questions?
Open an issue or check additional documentation in [HELP.md](HELP.md).

---

## 🇧🇷 Documentação em Português

Um encurtador de URLs simples, pronto para produção, feito com Java, Spring Boot e PostgreSQL. Inclui API RESTful, rastreamento de cliques, métricas de acesso (IP, user agent, timestamp), suporte a Docker e integração com Traefik.

---

### ✨ Funcionalidades

- 🔗 Encurte URLs com códigos únicos de 6 caracteres
- ↪️ Redirecione para URLs originais
- 📊 Rastreie cliques e logs de acesso (IP, user agent, timestamp)
- 🛠️ API RESTful para gerenciamento
- 🐳 Dockerizado para fácil deploy
- 🚦 Integração com Traefik para roteamento e proxy reverso

---

### 🛠️ Stack Tecnológica

- ☕ Java 21
- 🌱 Spring Boot 3
- 🐘 PostgreSQL
- 🐳 Docker & Docker Compose
- 🚦 Traefik (reverse proxy)
- 🗃️ JPA/Hibernate
- 🦾 Lombok
- ⚙️ Gradle

---

### 🚩 Como começar

#### Pré-requisitos
- Docker & Docker Compose
- Java 21 (para desenvolvimento local)
- Gradle

#### Rodando com Docker
1. **Clone o repositório:**
   ```sh
   git clone https://github.com/fsousac/weshorten.git
   cd weshorten
   ```
2. **Suba o stack:**
   ```sh
   docker compose up --build
   ```
3. **Acesse a aplicação:**
   - API: [http://short.local/api](http://short.local/api)
   - Links encurtados: [http://short.local/{shortCode}](http://short.local/{shortCode})
   - Obs: Adicione `127.0.0.1 short.local` ao seu `/etc/hosts` para testes locais.

---

### 📚 Endpoints da API

#### 🔸 Encurtar uma URL
- **POST** `/api/shorten`
- **Body:** `{ "originalUrl": "https://example.com" }`
- **Resposta:**
  ```json
  {
    "shortenedUrl": "https://short.local/abc123",
    "originalUrl": "https://example.com"
  }
  ```

#### 🔸 Listar todas as URLs encurtadas
- **GET** `/api/links`
- **Resposta:**
  ```json
  [
    {
      "shortenedUrl": "https://short.local/abc123",
      "originalUrl": "https://example.com",
      "clicks": 5
    }
  ]
  ```

#### 🔸 Deletar uma URL encurtada
- **DELETE** `/api/links/{shortCode}`

#### 🔸 Redirecionar
- **GET** `/{shortCode}`
- Redireciona para a URL original e registra métricas de acesso.

---

### 📈 Métricas Coletadas

A cada redirecionamento, são armazenados:
- Código curto
- Endereço IP
- User agent
- Timestamp de acesso (UTC)

---

### ⚙️ Configuração

- **Perfis:**
  - `dev`: Desenvolvimento local (`application-dev.properties`)
  - `prod`: Produção (`application-prod.properties`)
- **Banco de dados:**
  - PostgreSQL (veja `.env` para credenciais)

---

### 👨‍💻 Desenvolvimento

#### Rodando localmente
1. Inicie o PostgreSQL localmente ou via Docker.
2. Defina o profile ativo para dev:
   ```sh
   export SPRING_PROFILES_ACTIVE=dev
   ```
3. Build e execute:
   ```sh
   ./gradlew bootRun
   ```

#### Testes
Execute:
```sh
./gradlew test
```

---

### 📄 Licença

Distribuído sob a [GNU GPL v3](LICENSE).

---

### 🙋 Dúvidas?
Abra uma issue ou consulte a documentação adicional em [HELP.md](HELP.md).
