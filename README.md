# rest-spring-boot-jpa
A demo project for building a RESTful service with Spring HATEOAS.

A simple payroll service that manages the employees of a company. 

### Built With
- Java 11
- Spring Boot
- Spring HATEOS
- Spring JPA
- H2 Database
- Lombok
- Maven

### Clone Project

`git clone https://github.com/liviubiur/rest-spring-boot-jpa.git`


### Web API

Method | Description                 | URL          
-------| -------------------------|:-------------:
GET    | Return all the employee resources | /employees
GET    | Return an employee resource by ID | /employees/{id}
POST   | Save a new employee      | /employees
PUT    | Edit an employee resource by ID   | /employees/{id}
DELETE | Delete an employee resource by ID | /employees/{id}
GET    | Return all the order resources | /orders
GET    | Return an order resource by ID | /orders/{id}
POST   | Save a new order      | /orders
PUT    | Complete an order   | /orders/{id}/complete
DELETE | Cancel an order | /orders/{id}/cancel
