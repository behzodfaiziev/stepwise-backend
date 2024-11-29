# Project Setup

This project consists of a Spring Boot backend and a PostgreSQL database. Docker and Docker Compose
are used for containerization to streamline setup and configuration.

## Prerequisites

- Docker installed on your system
- Docker Compose installed
    - (Optional) IntelliJ IDEA or another IDE for Java development
- Note: sudo is required for Linux and MacOS users to run Docker commands, but not for Windows
  users.

## Getting Started

### 1. Clone the Repository

Clone this repository to your local machine:

```shell
git clone https://github.com/behzodfaiziev/stepwise-backend
cd https://github.com/behzodfaiziev/stepwise-backend
```

### 2. Configure Environment Variables

Create a .env file in the root of the project with the following content:

```shell
POSTGRES_USER=postgresql
POSTGRES_PASSWORD=password
POSTGRES_DB=mydb
JWT_SECRET=dGVzdFNlY3JldEtleU5vdEZvclByb2R1Y3Rpb24
GEMINI_API_KEY=your_gemini_api_key
```

### 3. Build and Run the Application with Docker Compose: Linux and MacOS

To start the PostgreSQL database and Spring Boot backend services, run the following command:

```shell
sudo docker-compose up -d --build
```

### 3.1 Running the Application with Docker Compose: Windows

Before running the application you have to run this command to convert mvnw file to Unix format:

```shell
dos2unix mvnw
```

Now, you can run the application with the following command:

```shell
docker-compose up -d --build
```

### 3.2 **If you need to start only the Database:** Linux, MacOS, and Windows

```shell
sudo docker-compose up -d db
```

### Optional. Running the Application Locally (Outside Docker)

If you want to run the backend outside of Docker (e.g., using IntelliJ IDEA), make sure to add the
.env file variables to the IntelliJ IDEA Run/Debug Configuration.

```shell
POSTGRES_DB=mydb;POSTGRES_PASSWORD=password;POSTGRES_USER=postgresql;JWT_SECRET=ZEdWemRGTmxZM0psZEV0bGVVNXZkRVp2Y2xCeWIyUjFZM1JwYjI0;GEMINI_API_KEY=your_gemini_api_key;GEMINI_API_SECRET=your_gemini_api_secret
```

### 5. Stopping the Application

To stop and remove all containers, use:

```shell
sudo docker-compose down
```

## Integration with Mobile App

- The mobile app for this project is built with Flutter and can be found at the following
  repository:
  [Stepwise](https://github.com/elffirem/StepWise)

- To integrate the backend with the mobile app, you need make sure that backend is running via
  **docker compose** and then run the mobile app.
- Now, you can read the instructions in the mobile app repository to set up the mobile
  app: [README.md](https://github.com/elffirem/StepWise/blob/main/README.md)