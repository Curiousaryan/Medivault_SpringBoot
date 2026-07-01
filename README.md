# 🏥 MediVault - Digital Medical Profile Management System

MediVault is a secure Spring Boot REST API that enables users to manage their personal medical information in one centralized platform. The application allows authenticated users to maintain medical profiles, medications, allergies, medical conditions, emergency contacts, and securely share medical records using public access tokens.

Built using modern Spring Boot practices including JWT Authentication, Spring Security, JPA, Docker Compose, and MySQL.

---

# ✨ Features

## 🔐 Authentication

- User Registration
- User Login
- JWT Authentication
- Password Encryption using Spring Security

---

## 👤 Medical Profile

- Create Medical Profile
- Update Medical Profile
- View Medical Profile
- Store personal medical information securely

---

## 💊 Medication Management

- Add Medications
- Update Medication
- Delete Medication
- View Medication List

---

## 🤒 Medical Conditions

- Add Medical Conditions
- Update Conditions
- Delete Conditions
- View Medical History

---

## 🌿 Allergy Management

- Add Allergies
- Update Allergy Information
- Delete Allergies
- View Allergy Records

---

## 🚑 Emergency Contacts

- Add Emergency Contacts
- Update Contact Details
- Delete Contacts
- View Emergency Contact List

---

## 🔗 Public Access Sharing

Generate secure public access tokens that allow authorized people to access selected medical information when required.

---

# 🏗 Architecture

The project follows a layered architecture.

```

Client
│
▼
Controller Layer
│
▼
Service Layer
│
▼
Repository Layer
│
▼
MySQL Database

```

Each layer has a single responsibility, making the project modular and easy to maintain.

---

# 📂 Project Structure

```

src
└── main
├── controller
├── service
│ ├── interfaces
│ └── implementations
├── repository
├── entity
├── dto
│ ├── request
│ └── response
├── security
├── config
└── resources

```

---

# 🛠 Tech Stack

### Backend

- Java 17
- Spring Boot
- Spring MVC

### Security

- Spring Security
- JWT Authentication

### Database

- MySQL
- Spring Data JPA
- Hibernate

### Validation

- Jakarta Validation

### Build Tool

- Maven

### DevOps

- Docker Compose

---

# 📦 Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot Starter Data JPA
- MySQL Connector
- Lombok
- Validation API

---

# ⚙ Installation

## Clone Repository

```bash
git clone https://github.com/Curiousaryan/Medivault_SpringBoot.git
```

---

## Navigate

```bash
cd Medivault_SpringBoot
```

---

## Configure Database

Update `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3308/medivault_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

---

## Run Docker

```bash
docker compose up
```

---

## Run Application

```bash
mvn spring-boot:run
```

---

Application will start on

```

http://localhost:8080

```

---

# 🔑 API Modules

## Authentication

```

POST /api/auth/register
POST /api/auth/login

```

---

## Medical Profile

```

GET
POST
PUT

```

---

## Medication

```

GET
POST
PUT
DELETE

```

---

## Allergy

```

GET
POST
PUT
DELETE

```

---

## Medical Condition

```

GET
POST
PUT
DELETE

```

---

## Emergency Contact

```

GET
POST
PUT
DELETE

```

---

## Public Access

```

Generate Access Token
Validate Token

```

---

# 🔒 Security

- JWT Authentication
- Password Encryption
- Protected REST APIs
- Stateless Authentication
- Role-based Security Ready

---

# 💾 Database

Main Entities

- User
- MedicalProfile
- Medication
- Allergy
- MedicalCondition
- EmergencyContact
- PublicAccessToken

---



# 👨‍💻 Author

**Aryan**

Java Backend Developer

GitHub:
https://github.com/Curiousaryan

---

If you found this project useful, consider giving it a ⭐.
