# 🎓 Student Enrollment System

A full-stack student enrollment system built with **Spring Boot**, **MongoDB**, **Docker**, and a sleek **React** frontend. It includes image upload, search, validations, and complete CRUD functionality — tested and verified with Postman and unit tests!

![Project Banner](https://github.com/deshanlankal/Student-Registration-NCINGA/blob/main/Images/Registration-Problems.gif)

---

## 🚀 Features

### Backend (Spring Boot + MongoDB)
- 🧾 Create, Read, Update, Delete students
- 🖼 Upload and update profile image
- 🔐 Secure cross-origin communication
- 📸 Multipart form handling
- 🧪 Fully tested (Controller & Service Layer)

### Frontend (React.js)
- 🎨 Clean, dark-themed UI with modern floating inputs
- ✅ Validations:
  - No numbers in first/last name
  - Valid email format
  - No letters in phone number
- 🔄 Flip card UI for student details
- 🌟 Image preview before upload
- 📱 Responsive and animated interface

### DevOps (Docker)
- 🐳 Dockerized backend
- 🛠 Easy spin-up with Docker Compose

---

## 🧰 Tech Stack

| Tech           | Usage                          |
|----------------|--------------------------------|
| Java + Spring Boot | REST API, Validation, Services |
| MongoDB        | NoSQL database                |
| React.js       | Frontend                      |
| Postman        | API Testing                   |
| Docker         | Containerization              |
| JUnit + MockMvc| Unit Testing                  |

---

## 📸 Screenshots

### ✅ Unit Test Results
![Service Test Pass](https://github.com/deshanlankal/Student-Registration-NCINGA/blob/main/Images/testService.png)
![Controller Test Pass](https://github.com/deshanlankal/Student-Registration-NCINGA/blob/main/Images/testController.png)

### 📬 Postman Requests
![Create Student](https://github.com/deshanlankal/Student-Registration-NCINGA/blob/main/Images/CreateStudent.png)
![Get Students By ID](https://github.com/deshanlankal/Student-Registration-NCINGA/blob/main/Images/getStudentByID.png)
![Get All Students](https://github.com/deshanlankal/Student-Registration-NCINGA/blob/main/Images/getAllStudents.png)
![Remove Student BY ID](https://github.com/deshanlankal/Student-Registration-NCINGA/blob/main/Images/deletebyID.png)

### 🖼 UI Preview
![React UI Form](https://github.com/deshanlankal/Student-Registration-NCINGA/blob/main/Images/form.png)  
![Card View](https://github.com/deshanlankal/Student-Registration-NCINGA/blob/main/Images/UIcard.png)

---

## 🔧 Endpoints

| Method | Endpoint                          | Description                 |
|--------|-----------------------------------|-----------------------------|
| POST   | `/api/students`                   | Add student (JSON only)     |
| POST   | `/api/students/student`           | Add student with image      |
| GET    | `/api/students`                   | Fetch all students          |
| GET    | `/api/students/{id}`              | Fetch by ID                 |
| PUT    | `/api/students/student/{id}`      | Update student (optional image) |
| DELETE | `/api/students/{id}`              | Delete by ID                |
| GET    | `/api/students/email/{email}`     | Fetch by Email              |
| GET    | `/api/students/phone/{phone}`     | Fetch by Phone              |

---

## 🐳 Run with Docker

1. Clone the repo  
   ```bash
   git clone https://github.com/yourusername/student-enrollment-app.git
   cd student-enrollment-app
2. Build the Spring Boot backend manually
   ```bash
   ./mvnw clean compile package -DskipTests
3. Build the Spring Boot backend manually (optional)
    ```bash
    ./mvnw clean compile package -DskipTests
4. Build Docker image manually
    ```bash
     docker build -t student-enrollment-backend .
5. Run the container manually
    ```bash
    docker run -d -p 8080:8080 --name student-backend student-enrollment-backend
6. Start all services using Docker Compose
    ```bash
    docker-compose -f docs/docker-compose.yml up --build
7. Check logs
    ```bash
    docker-compose logs -f
8. Stop and remove containers
   ```bash
   docker-compose down

## 🌐 Access the Application

- **Backend API**: [http://localhost:8080](http://localhost:8080)  
- **Frontend UI**: [http://localhost:3000](http://localhost:3000)
```
