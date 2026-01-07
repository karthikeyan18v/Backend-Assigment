# Backend Assignment – Spring Boot

This project implements a minimal backend service using **Java 17** and **Spring Boot**, covering two independent use cases:

1. **Ride-Hailing**
2. **Tutor / Live-Class Booking**

The goal of this assignment is to demonstrate **API design, correctness, state management, concurrency handling, testing, and clean code structure**.

---

## 🧱 Tech Stack

- **Java 17**
- **Spring Boot 3.x**
- Spring Web & Validation
- In-memory persistence (`ConcurrentHashMap`)
- JUnit 5 (unit & concurrency tests)
- Docker 

No external databases or services are required.

---

## 🚀 How to Run the Application

### Run locally

```bash
./mvnw spring-boot:run
```
---
## 🧪 How to Run Tests

Run all unit and concurrency tests:
```bash 
./mvnw test
```
---

## Assumptions & Trade-Offs (MANDATORY)
* Assumptions
*No authentication or user management
*Drivers and students are represented by IDs
*ETA is simulated
*All data is in-memory
---
## 📡 API Usage — curl Examples

Base URL: http://localhost:8080

1️⃣ Request a Ride
```bash
curl -X POST http://localhost:8080/rides/request \
-H "Content-Type: application/json" \
-d '{
  "userId": "u1",
  "pickup": { "lat": 12.95, "lon": 77.59 },
  "drop": { "lat": 12.97, "lon": 77.60 }
}'
```
2️⃣ Update Ride Status
```bash 
curl -X POST http://localhost:8080/rides/r1/status \
-H "Content-Type: application/json" \
-d '{ "status": "ACCEPTED" }'

curl -X POST http://localhost:8080/rides/r1/status \
-H "Content-Type: application/json" \
-d '{ "status": "STARTED" }'

curl -X POST http://localhost:8080/rides/r1/status \
-H "Content-Type: application/json" \
-d '{ "status": "COMPLETED" }'
```
❌ Invalid Ride Status Transition (Validation Check)
```bash
curl -X POST http://localhost:8080/rides/r1/status \
-H "Content-Type: application/json" \
-d '{ "status": "REQUESTED" }'
```
3️⃣ Get Nearby Drivers
```bash
curl "http://localhost:8080/drivers/nearby?lat=12.95&lon=77.59&radiusKm=5"
```
---
## 👨‍🏫 Tutor / Live-Class APIs
4️⃣ Create a Class Session
```bash
curl -X POST http://localhost:8080/classes \
-H "Content-Type: application/json" \
-d '{
  "teacherId": "t1",
  "title": "Math Basics",
  "startTime": "2026-01-15T15:00:00Z",
  "durationMinutes": 60,
  "capacity": 5
}'
```
5️⃣ Book a Seat
```bash
curl -X POST http://localhost:8080/classes/c1/book \
-H "Content-Type: application/json" \
-d '{ "studentId": "s1" }'
```
6️⃣ List Class Attendees
```bash
curl http://localhost:8080/classes/c1/attendees
```
Start Class
```bash
curl -X POST http://localhost:8080/classes/c1/start
```
## All outputs are available in the `images` folder.

