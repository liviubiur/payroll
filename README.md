# payroll
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

`git clone https://github.com/liviubiur/payroll.git`


### Web API

Method | Description                 | URL          
-------| -------------------------|:-------------:
GET    | Return all the employee resources | /payroll-service/employees
GET    | Return an employee resource by ID | /payroll-service/employees/{id}
POST   | Save a new employee      | /payroll-service/employees
PUT    | Edit an employee resource by ID   | /payroll-service/employees/{id}
DELETE | Delete an employee resource by ID | /payroll-service/employees/{id}
GET    | Return all the order resources | /payroll-service/orders
GET    | Return an order resource by ID | /payroll-service/orders/{id}
POST   | Save a new order      | /payroll-service/orders
PUT    | Complete an order   | /payroll-service/orders/{id}/complete
DELETE | Cancel an order | /payroll-service/orders/{id}/cancel
