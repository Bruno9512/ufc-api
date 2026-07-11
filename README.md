# 🥊 UFC API

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-6DB33F?style=for-the-badge&logo=springsecurity)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger)
![JUnit](https://img.shields.io/badge/JUnit-5-green?style=for-the-badge&logo=junit5)
![Mockito](https://img.shields.io/badge/Mockito-Testes-brightgreen?style=for-the-badge)
![JaCoCo](https://img.shields.io/badge/JaCoCo-90%25-success?style=for-the-badge)

API REST desenvolvida em **Java + Spring Boot** para gerenciamento de lutadores do UFC.

---

# 📚 Tecnologias

- Java 21
- Spring Boot
- Spring Security
- JWT
- MySQL
- Docker
- Swagger
- JUnit 5
- Mockito
- JaCoCo

---

# 🏗 Arquitetura

Controller

↓

Service

↓

Repository

↓

MySQL

---

# 🔐 Autenticação

- Cadastro de usuários
- Login
- JWT
- Roles (ADMIN / USER)

---

# 📖 Documentação da API

A documentação é disponibilizada através do Swagger.

```
http://localhost:8080/swagger-ui/index.html
```

## Interface Swagger

![Swagger](images/swagger.png)

---

# 🐳 Docker

A aplicação pode ser executada totalmente via Docker Compose.

```
docker compose up --build
```

### Containers em execução

![Docker](images/Docker.png)

---

# 🧪 Testes

O projeto possui testes utilizando:

- JUnit 5
- Mockito
- Spring Boot Test

Cobertura aproximada:

**90%**

### Relatório JaCoCo

![JaCoCo](images/JaCoCo.png)

---

# 🚀 Endpoints

## Lutadores

- GET /lutadores
- GET /lutadores/{id}
- POST /lutadores
- PUT /lutadores/{id}
- DELETE /lutadores/{id}

## Autenticação

- POST /auth/login
- POST /auth/register
- GET /auth/me

---

# 📮 Testes com Postman

Todas as rotas podem ser testadas utilizando o Postman.

![Postman](images/Postman.png)

---

# ⚙ Como executar

## Clonar

```
git clone https://github.com/Bruno9512/ufc-api.git
```

## Entrar no projeto

```
cd ufc-api
```

## Subir containers

```
docker compose up --build
```

## Executar

```
mvn spring-boot:run
```

---

# 👨‍💻 Autor

Bruno Souza

Projeto desenvolvido durante os estudos de **Análise e Desenvolvimento de Sistemas**, com foco em desenvolvimento Backend utilizando Spring Boot.
