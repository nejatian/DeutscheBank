# Payment Processing Service

## Overview

This Spring Boot application processes payment transactions and calculates various financial metrics, including:

- Highest and lowest EUR values for a given date
- Total transaction volume in EUR
- Outstanding amounts per company
- Outstanding amounts per currency

The application reads payments and currency exchange rates from files stored in the `resources/data/` directory.

## Technologies Used

- **Java 17**
- **Spring Boot** (Spring MVC, Spring Test)
- **JUnit 5** (for unit and integration testing)
- **Mockito** (for mocking dependencies in unit tests)
- **Lombok** (for reducing boilerplate code)
- **Maven** (for dependency management)

## Project Structure

```
src/main/java/task/db/
│── controller/       # REST API controllers
│── service/          # Business logic layer
│── model/            # Data models
│── mapper/           # Mappers for parsing input files
│── exception/        # Custom exception handling
│── resources/data/   # Input files (payments.txt, currency_rates.txt)
│── test/             # Unit and integration tests
```

## Endpoints

| Endpoint                                    | Method | Description                                      |
| ------------------------------------------- | ------ | ------------------------------------------------ |
| `/payments/highest-eur/{date}`              | GET    | Returns the highest EUR value for the given date |
| `/payments/lowest-eur/{date}`               | GET    | Returns the lowest EUR value for the given date  |
| `/payments/transaction-volume/{date}`       | GET    | Returns the total transaction volume in EUR      |
| `/payments/outstanding-per-company/{date}`  | GET    | Returns outstanding amounts per company in EUR   |
| `/payments/outstanding-per-currency/{date}` | GET    | Returns outstanding amounts per currency         |

## Setup & Running the Application

### Prerequisites

- Java 17 or later
- Maven

### Steps

1. Clone the repository:
   ```sh
   git clone <repository-url>
   cd <project-directory>
   ```
2. Build the project:
   ```sh
   mvn clean install
   ```
3. Run the application:
   ```sh
   mvn spring-boot:run
   ```
4. Access the API via `http://localhost:8080/payments`.

## Running Tests

To execute unit and integration tests, run:

```sh
mvn test
```

## Error Handling

The application uses custom exception handling to manage errors, such as file loading issues.

## Future Enhancements

- Support for additional currencies
- Database integration instead of file-based storage

---

This project is maintained by **Sarvenaz Nejatian**. Feel free to contribute or report issues!

