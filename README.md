# todos

A small REST API for managing to-do items, built with Spring Boot and Kotlin.

## Tech stack

- **Kotlin** on **Java 21**
- **Spring Boot 3.3** (Web, Data JPA)
- **PostgreSQL** in production, **H2** (in-memory) for tests
- **Flyway** for database migrations
- **Gradle** (Kotlin DSL) build
- **JUnit 5** + **MockK** for testing

## Project layout

```
src/main/kotlin/io/jay/todos
├── controller   # REST endpoints + request/response DTOs
├── service      # business logic
├── repository   # persistence (JPA + repository abstraction)
├── entity       # JPA entities
└── model        # domain model
src/main/resources
├── application.yml               # datasource config (default + test profiles)
└── db/migration                  # Flyway migrations
```

## Prerequisites

- JDK 21
- A running PostgreSQL instance with a database named `todo`

The default datasource (`src/main/resources/application.yml`) expects:

| Setting  | Value                                   |
|----------|-----------------------------------------|
| URL      | `jdbc:postgresql://localhost:5432/todo` |
| Username | `postgres`                              |
| Password | *(empty)*                               |

Flyway applies the schema on startup.

## Running

Build and run the application:

```bash
./gradlew bootRun
```

The API starts on `http://localhost:8080`.

## Testing

Tests run against an in-memory H2 database (the `test` profile), so no
PostgreSQL is required:

```bash
./gradlew test
```

## API

Base path: `/api/todos`

| Method   | Path              | Description        | Success status |
|----------|-------------------|--------------------|----------------|
| `GET`    | `/api/todos`      | List all todos     | `200 OK`       |
| `GET`    | `/api/todos/{id}` | Get a todo by id   | `200 OK`       |
| `POST`   | `/api/todos`      | Create a new todo  | `201 Created`  |
| `DELETE` | `/api/todos/{id}` | Delete a todo      | `204 No Content` |

Unknown ids on `GET /{id}` and `DELETE /{id}` return `404 Not Found`.

A health check is available at `GET /health` (returns `200 OK`).

### Examples

Create a todo:

```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"description": "Buy milk"}'
```

Response (`201 Created`):

```json
{ "id": 1, "description": "Buy milk", "finished": false }
```

List todos:

```bash
curl http://localhost:8080/api/todos
```

Delete a todo:

```bash
curl -X DELETE http://localhost:8080/api/todos/1
```

## Deployment

A Cloud Foundry `manifest.yml` is included. Build the jar and push:

```bash
./gradlew build
cf push
```
