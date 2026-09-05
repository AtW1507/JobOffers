# 💼 JobOffers - Junior Java Developer Job Aggregator

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-NoSQL-47A248.svg)](https://www.mongodb.com/)
[![Redis](https://img.shields.io/badge/Redis-Cache-DC382D.svg)](https://redis.io/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg)](https://www.docker.com/)

## 📖 About the Project
**JobOffers** is a comprehensive backend web application designed to aggregate job postings specifically for **Junior Java Developers**. The core functionality of the system is to periodically fetch the latest job offers from various external sources (websites, other web applications) and centralize them in a single database.

This project was built focusing on modern backend architecture, utilizing a robust tech stack, secure communication, high-quality testing practices, and agile methodologies.

## 🚀 Core Features
*   **Automated Data Fetching:** Utilizes `Spring Scheduler` and `RestTemplate` to periodically reach out to external HTTP sources, download, and parse new job offers.
*   **Caching with Redis:** Implemented `Redis` (via `Jedis`) to cache frequently accessed data, significantly reducing database load and improving response times.
*   **Authentication & Security:** Secured endpoints using `Spring Security` and `JWT` (JSON Web Tokens). Only authorized users can access specific resources.
*   **Data Storage:** Persists data using `MongoDB` (NoSQL), ensuring flexible schema management and fast read/write operations for job documents.
*   **Data Validation:** Integrated Spring `Validation` to ensure data integrity at the API level.

## 🛠️ Technology Stack & Methodologies

**Backend & Frameworks:**
*   Java 17
*   Spring Boot (Web, Data MongoDB, Security, Validation)
*   Spring Scheduler
*   JWT (JSON Web Tokens)
*   RestTemplate, JSON, HTTP
*   Lombok, Log4j2, Maven

**Databases & Caching:**
*   MongoDB + MongoExpress (UI for MongoDB)
*   Redis + Jedis + Redis-Commander (UI for Redis)

**Testing (Unit & Integration):**
*   JUnit 5, Mockito, AssertJ
*   SpringBootTest, MockMvc, SpringSecurityTest
*   **WireMock:** Mocking external APIs for reliable integration testing.
*   **Testcontainers:** Spinning up ephemeral Docker containers for MongoDB and Redis during tests.
*   **Awaitility:** Testing asynchronous systems and scheduled tasks.

**DevOps & Tools:**
*   Docker, Docker-Compose, Docker Desktop
*   Jenkins (CI/CD)
*   Git, GitHub / GitLab
*   Swagger (API Documentation)
*   IntelliJ IDEA Ultimate

**Workflow & Methodologies:**
*   Agile / SCRUM (Managed via Jira)
*   Pair Programming
*   Code Review practices

## ⚙️ Running the Project Locally

### Prerequisites
Make sure you have the following installed on your machine:
*   [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
*   [Maven](https://maven.apache.org/download.cgi)
*   [Docker & Docker Desktop](https://www.docker.com/)

### Step-by-Step Guide

1. **Clone the repository:**
   ```bash
   git clone https://github.com/AtW1507/JobOffers.git
   cd JobOffers
   ```

2. **Start the Infrastructure (Databases & UI):**
   Use Docker Compose to spin up MongoDB, MongoExpress, Redis, and Redis-Commander in the background:
   ```bash
   docker-compose up -d
   ```
   *Note: You can access MongoExpress and Redis-Commander via your browser to inspect the data visually (check your `docker-compose.yml` for specific ports, usually 8081 for MongoExpress and 8082 for Redis-Commander).*

3. **Build the application and run tests:**
   ```bash
   mvn clean install
   ```

4. **Run the Spring Boot application:**
   ```bash
   mvn spring-boot:run
   ```

## 📡 API Endpoints
Once the application is running (default port `8080`), you can access the interactive API documentation provided by **Swagger**:
👉 `http://localhost:8080/swagger-ui/index.html`

*(Note: Endpoints requiring authorization need a valid JWT token passed in the `Authorization: Bearer <token>` header).*

## 🧪 Testing Strategy
Quality assurance is a primary focus of this project. The application is covered by both Unit and Integration tests:
*   **Testcontainers** guarantee that all database and cache interactions are tested against real, isolated instances of MongoDB and Redis, preventing environment-specific bugs.
*   **WireMock** is used to stub external job provider APIs, ensuring the test suite runs fast and remains completely independent of external network availability.
*   **Awaitility** is heavily utilized to test `@Scheduled` tasks, verifying that background fetching mechanisms execute correctly over time.
*   **SpringSecurityTest** ensures that JWT generation, validation, and endpoint protection work as expected.

To execute the test suite:
```bash
mvn test
```