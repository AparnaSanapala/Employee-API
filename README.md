# Employee Management REST API

A Spring Boot RESTful API for managing employee records.

## Endpoints

Create Employee (Basic) | POST | `/employees` | Create employee with name and email |
Create Employee (With Phone) | POST | `/employees/full` | Create employee with phone |
Fetch by Email | GET | `/employees/{type}/email/{email}` | Get by email using JPA, HQL, Native |
Fetch by Name | GET | `/employees/{type}/name/{name}` | Get by name using JPA, HQL, Native |
Update Details | PUT | `/employees/{email}` | Update last name, phone, address |
Update Phone | PATCH | `/employees/phone/{email}` | Update phone only |
Delete by Email | DELETE | `/employees/email/{email}` | Delete employee |

## Tools & Technologies
- Spring Boot
- Spring Data JPA
- H2 Database
- Postman (for testing)
- Maven (build tool)
- Mockito & JUnit (testing)
