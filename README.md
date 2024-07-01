# Backend-Maven-Tomcat-Movies

## Description
This project is a Maven application for the frontend project Movie Management from the Codo a Codo course Q1 2024. It provides a REST API for the Movies and Users with CRUD operations.

## Installation
To run this project there are two options:
1. Run the project from an IDE
- Make sure to have installed [JDK 22](https://www.oracle.com/ar/java/technologies/downloads/), as the project requires it.
- Clone the project from the repository
- Open the project from your IDE
- Run the project as a Maven application
  
2. Run the project from as a container using Docker
- Make sure to have installed Docker
- Clone the project from the repository
- Build the project using the command `./gradlew clean build`
- Build the docker image using the command `docker build -t springboot-movies .`
- Pull the MySQL image using the command `docker pull mysql`
- Create a network using the command `docker network create internalnet`
- Run the MySQL container using the command `docker run --name mysql --network internalnet -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=movies -p 3306:3306 -d mysql`
- Run the Springboot container using the command `docker run --name springboot-movies --network internalnet -p 8080:8080 -d springboot-movies`

## Usage
Once the project is running you can use the following endpoints:

### Movies
- GET /movies: Get all the movies
- POST /movie: Create a new movie
- PUT /movie/{id}: Update a movie by id
- DELETE /movie/{id}: Delete a movie by id

### Users
- GET /users: Get all the users
- POST /user: Create a new user
- PUT /user/{id}: Update a user by id
- DELETE /user/{id}: Delete a user by id

## License
Apache License, Version 2.0

## Project status
This project is in development and is part of the Codo a Codo course Q1 2024.

