# 🥊 UFC API

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-6DB33F?style=for-the-badge&logo=springsecurity)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger)
![JUnit 5](https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5)
![Mockito](https://img.shields.io/badge/Mockito-Testes-78A641?style=for-the-badge)
![JaCoCo](https://img.shields.io/badge/JaCoCo-90%25-success?style=for-the-badge)

API REST desenvolvida em **Java + Spring Boot** para gerenciamento de lutadores do UFC, utilizando autenticação JWT, Docker, Swagger e testes automatizados.
---

# Tecnologias utilizadas

* Java 21
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA
* MySQL
* Docker
* Docker Compose
* Swagger / OpenAPI
* Maven
* JUnit 5
* Mockito
* JaCoCo

---

# Arquitetura

```
Controller
     ↓
Service
     ↓
Repository
     ↓
MySQL
```

A aplicação utiliza arquitetura em camadas para separar responsabilidades e facilitar manutenção e testes.

---

# Funcionalidades

## Autenticação

* Cadastro de usuários
* Login
* Geração de Token JWT
* Autorização baseada em Roles

### Roles

* ADMIN
* USER

---

## Lutadores

* Listar todos
* Buscar por ID
* Buscar por nome
* Buscar por país
* Buscar por categoria
* Buscar por altura
* Cadastrar
* Atualizar
* Excluir

---

# Segurança

A autenticação é realizada utilizando **JWT**.

Após efetuar login, o usuário recebe um token que deve ser enviado em todas as requisições protegidas.

Exemplo:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

# Documentação

A documentação da API é disponibilizada através do Swagger.

Após iniciar a aplicação:

```
http://localhost:8080/swagger-ui/index.html
```

---

# Docker

O projeto pode ser executado utilizando Docker Compose.

Subir os containers:

```
docker compose up --build
```

Parar os containers:

```
docker compose down
```

---

# Estrutura do projeto

```
src
 ├── main
 │   ├── config
 │   ├── controller
 │   ├── dto
 │   ├── exception
 │   ├── mapper
 │   ├── model
 │   ├── repository
 │   ├── security
 │   └── service
 │
 └── test
     ├── controller
     ├── exception
     ├── security
     └── service
```

---

# Testes

O projeto possui testes unitários utilizando:

* JUnit 5
* Mockito
* Spring Boot Test

Foram testados:

* Controllers
* Services
* JWT
* Exception Handler

Cobertura atual aproximada:

**90% utilizando JaCoCo**

Executar os testes:

```
mvn test
```

Gerar relatório:

```
mvn clean verify
```

Relatório:

```
target/site/jacoco/index.html
```

---

# Banco de Dados

Banco utilizado:

MySQL

O banco pode ser iniciado automaticamente através do Docker Compose.

O script de inicialização encontra-se em:

```
docker/mysql/init.sql
```

---

# Como executar

## Clonar

```
git clone https://github.com/SEU-USUARIO/ufc-api.git
```

## Entrar no projeto

```
cd ufc-api
```

## Subir banco

```
docker compose up -d
```

## Executar aplicação

```
mvn spring-boot:run
```

---

# Autor

Bruno Souza

Projeto desenvolvido como parte dos estudos em **Análise e Desenvolvimento de Sistemas**, com foco em desenvolvimento Backend utilizando Spring Boot.
