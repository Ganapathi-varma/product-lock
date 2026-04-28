# Product Lock API

Product Lock Management API built with Spring Boot.

---

## Prerequisites

Install the required modules before running:

```bash
module add maven/3.9.9
module add java/21
```

Verify installation:

```bash
mvn -version
java -version
```

---

## Run the Application

```bash
mvn clean spring-boot:run
```

The application starts on `http://localhost:8080`.

---

## API Endpoints

Base URL: `http://localhost:8080`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Service health check |
| POST | `/api/v1/product-locks` | Create a new lock |
| GET | `/api/v1/product-locks` | Get all locks |
| GET | `/api/v1/product-locks/{id}` | Get lock by ID |
| GET | `/api/v1/product-locks/product/{productNumber}/version/{version}` | Get lock by product number and version |
| GET | `/api/v1/product-locks/product/{productNumber}` | Get all locks for a product |
| GET | `/api/v1/product-locks/product/{productNumber}/version/{version}/status` | Check lock status |
| PUT | `/api/v1/product-locks/product/{productNumber}/version/{version}` | Update a lock |
| DELETE | `/api/v1/product-locks/product/{productNumber}/version/{version}` | Delete a lock |

---

## Usage Examples

### 1. Service Health Check

```bash
curl http://localhost:8080/
```

Response (`200 OK`):

```json
{
  "status": "UP",
  "message": "Product Lock Service is up and running"
}
```

### 2. Create a Lock

```bash
curl -X POST http://localhost:8080/api/v1/product-locks \
  -H "Content-Type: application/json" \
  -d '{
    "productNumber": "PRD-001",
    "version": "1.0",
    "locked": true,
    "lockedBy": "username",
    "reason": "Release freeze"
  }'
```

Response (`201 Created`):

```json
{
  "id": 1,
  "productNumber": "PRD-001",
  "version": "1.0",
  "locked": true,
  "lockedBy": "username",
  "reason": "Release freeze",
  "createdAt": "2026-04-28T10:00:00",
  "updatedAt": "2026-04-28T10:00:00"
}
```

### 3. Get All Locks

```bash
curl http://localhost:8080/api/v1/product-locks
```

Response (`200 OK`):

```json
[
  {
    "id": 1,
    "productNumber": "PRD-001",
    "version": "1.0",
    "locked": true,
    "lockedBy": "username",
    "reason": "Release freeze",
    "createdAt": "2026-04-28T10:00:00",
    "updatedAt": "2026-04-28T10:00:00"
  }
]
```

### 4. Get Lock by ID

```bash
curl http://localhost:8080/api/v1/product-locks/1
```

Response (`200 OK`):

```json
{
  "id": 1,
  "productNumber": "PRD-001",
  "version": "1.0",
  "locked": true,
  "lockedBy": "username",
  "reason": "Release freeze",
  "createdAt": "2026-04-28T10:00:00",
  "updatedAt": "2026-04-28T10:00:00"
}
```

### 5. Get Lock by Product Number and Version

```bash
curl http://localhost:8080/api/v1/product-locks/product/PRD-001/version/1.0
```

Response (`200 OK`):

```json
{
  "id": 1,
  "productNumber": "PRD-001",
  "version": "1.0",
  "locked": true,
  "lockedBy": "username",
  "reason": "Release freeze",
  "createdAt": "2026-04-28T10:00:00",
  "updatedAt": "2026-04-28T10:00:00"
}
```

### 6. Get All Locks for a Product

```bash
curl http://localhost:8080/api/v1/product-locks/product/PRD-001
```

Response (`200 OK`):

```json
[
  {
    "id": 1,
    "productNumber": "PRD-001",
    "version": "1.0",
    "locked": true,
    "lockedBy": "username",
    "reason": "Release freeze",
    "createdAt": "2026-04-28T10:00:00",
    "updatedAt": "2026-04-28T10:00:00"
  },
  {
    "id": 2,
    "productNumber": "PRD-001",
    "version": "2.0",
    "locked": false,
    "lockedBy": "username",
    "reason": "Released",
    "createdAt": "2026-04-28T11:00:00",
    "updatedAt": "2026-04-28T11:30:00"
  }
]
```

### 7. Check Lock Status

```bash
curl http://localhost:8080/api/v1/product-locks/product/PRD-001/version/1.0/status
```

Response (`200 OK`):

```json
{
  "locked": true
}
```

### 8. Update a Lock (e.g. Unlock)

```bash
curl -X PUT http://localhost:8080/api/v1/product-locks/product/PRD-001/version/1.0 \
  -H "Content-Type: application/json" \
  -d '{
    "productNumber": "PRD-001",
    "version": "1.0",
    "locked": false,
    "lockedBy": "username",
    "reason": "Release complete"
  }'
```

Response (`200 OK`):

```json
{
  "id": 1,
  "productNumber": "PRD-001",
  "version": "1.0",
  "locked": false,
  "lockedBy": "username",
  "reason": "Release complete",
  "createdAt": "2026-04-28T10:00:00",
  "updatedAt": "2026-04-28T12:00:00"
}
```

### 9. Delete a Lock

```bash
curl -X DELETE http://localhost:8080/api/v1/product-locks/product/PRD-001/version/1.0
```

Response: `204 No Content`

---

## Request Body Fields

| Field | Type | Required | Default | Description |
|-------|------|----------|---------|-------------|
| productNumber | String | Yes | - | Product identifier |
| version | String | Yes | - | Product version |
| locked | Boolean | No | true | Lock state |
| lockedBy | String | No | null | Who locked it |
| reason | String | No | null | Reason for locking |

## Response Fields

| Field | Type | Description |
|-------|------|-------------|
| id | Long | Auto-generated lock ID |
| productNumber | String | Product identifier |
| version | String | Product version |
| locked | Boolean | Current lock state |
| lockedBy | String | Who locked it |
| reason | String | Reason for locking |
| createdAt | DateTime | When the lock was created |
| updatedAt | DateTime | When the lock was last updated |

## Error Responses

| Status | Reason | Example |
|--------|--------|---------|
| 400 | Validation failed (missing required fields) | `{"status": 400, "error": "Validation Failed", "errors": {"productNumber": "Product number is required"}}` |
| 404 | Lock not found | `{"status": 404, "error": "Not Found", "message": "Product lock not found for product 'PRD-001' version '1.0'"}` |
| 409 | Lock already exists for product+version | `{"status": 409, "error": "Conflict", "message": "Lock already exists for product PRD-001 version 1.0"}` |
| 500 | Unexpected server error | `{"status": 500, "error": "Internal Server Error", "message": "An unexpected error occurred"}` |
