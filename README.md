# MetaGen Terminal

MetaGen Terminal is a modular trading platform built with Spring Boot. It supports real-time trading operations, expert advisor integration, risk management, trade tracking, and user-role-based access control with JWT authentication.

---

## 🧩 Modules

| Module Name         | Description                                 | Port |
|---------------------|---------------------------------------------|------|
| `user-service`      | Manages registration, login, and roles      | 8081 |
| `order-service`     | Handles order placement and execution       | 8082 |
| `market-service`    | Simulates or fetches live market prices     | 8083 |
| `risk-service`      | Performs risk checks before execution       | 8084 |
| `trade-service`     | Stores trade history and execution records  | 8085 |
| `expert-service`    | Accepts signals from EA and logs actions    | 8086 |

---

## 🔐 Features

- JWT-based authentication & Role-based access (Admin/User)
- Modular Spring Boot services (REST APIs)
- Expert Advisor (EA) JSON signal integration
- Risk check integration before trade execution
- Centralized trade logging and history

---

## 🚀 Getting Started

1. Clone the repo:
   ```bash
   git clone https://github.com/your-username/metagen-terminal.git
   cd metagen-terminal
