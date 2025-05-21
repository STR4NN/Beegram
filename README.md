# 🐝 Beegram - Social Network (Spring Boot)

Beegram is a backend project for a social network platform built using **Java** and **Spring Boot**. It allows users to register, log in, create posts, interact with others, and manage personal profiles.

## 🚀 Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- MySQL
- Maven


## ✨ Features

- User registration and login
- JWT-based authentication and authorization
- Create, edit, and delete posts
- Like and comment on posts
- User profiles with photo and bio
- Public feed with posts

## 📦 How to Run the Project Locally

### Prerequisites

- Java 17+
- Maven installed
- MySQL server running
- An IDE (e.g., IntelliJ, Eclipse, VS Code)

### Steps

1. Clone the repository:  
   `git clone https://github.com/your-username/beegram.git`

2. Navigate to the project folder:  
   `cd beegram`

3. Open the file `src/main/resources/application.properties` and configure your MySQL credentials:
'''
spring.datasource.url=jdbc:mysql://localhost:3306/beegram
spring.datasource.username=your_mysql_user
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
'''
4. Run the project with Maven:  
`./mvnw spring-boot:run`  
_(On Windows, use `mvnw.cmd spring-boot:run`)_

5. Open your browser and go to:  
`http://localhost:8080`
