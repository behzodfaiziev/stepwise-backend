# Project Setup

This project consists of a Spring Boot backend and a PostgreSQL database. Docker and Docker Compose
are used for containerization to streamline setup and configuration.

## Prerequisites

- Docker installed on your system
- Docker Compose installed
    - (Optional) IntelliJ IDEA or another IDE for Java development

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
```

### 3. Build and Run the Application with Docker Compose

To start the PostgreSQL database and Spring Boot backend services, run the following command:

```shell
docker-compose up -d --build
```

- **If you need to start only the Database:**

```shell
docker-compose up -d db
```

### 4. Running the Application Locally (Outside Docker)

If you want to run the backend outside of Docker (e.g., using IntelliJ IDEA), make sure to add the
.env file variables to the IntelliJ IDEA Run/Debug Configuration.

```shell
POSTGRES_DB=mydb;POSTGRES_PASSWORD=password;POSTGRES_USER=postgresql;JWT_SECRET=ZEdWemRGTmxZM0psZEV0bGVVNXZkRVp2Y2xCeWIyUjFZM1JwYjI0
```

### 5. Stopping the Application

To stop and remove all containers, use:

```shell
docker-compose down
```