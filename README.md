# 🏋️ Fitness Tracker API (Spring Boot 3.4+)

A robust, production-ready REST API for logging workouts and tracking fitness goals, built with a focus on data integrity and concurrency management.

## 🛠️ Tech Stack
- **Java 21**
- **Spring Boot 3.4+** (Web, Data JPA, Validation)
- **H2 Database** (In-memory for development)
- **Maven** (Dependency Management)

## 🚀 Key Features
- **Concurrent Progress Tracking:** Uses **Optimistic Locking (@Version)** to prevent data loss during simultaneous updates.
- **Robust Validation:** Integrated Global Exception Handling to return clean, structured error responses for invalid inputs.
- **Auto-Calorie Logic:** Intelligent service layer that calculates calories burned based on duration if not provided.
- **Transactional Integrity:** All service operations are wrapped in `@Transactional` to ensure "all-or-nothing" database consistency.

## 🚦 API Endpoints
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| POST | `/api/workouts` | Log a new workout (includes validation) |
| GET | `/api/workouts` | Fetch all workouts (supports Pagination) |
| DELETE | `/api/workouts/{id}` | Remove a workout safely |

## 🧪 How to Run
1. Clone the repo.
2. Run `./mvnw spring-boot:run`.
3. Access H2 Console at `localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:fitnessdb`).

---
Developed by a 2026 B.Tech IT Student | Chennai Institute of Technology