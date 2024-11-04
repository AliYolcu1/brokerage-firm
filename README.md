# Brokerage Firm Application

This is a Spring Boot application for managing a brokerage firm's operations.

## Features

- Customer management
- Asset tracking
- Order processing (Buy/Sell)
- Money transfer operations (Deposit/Withdraw)

## Technologies

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database
- Maven
- Lombok
- MapStruct

## Getting Started

### Prerequisites

- JDK 17 or later
- Maven 3.6 or later

### Installation

1. Clone the repository
```bash
git clone https://github.com/your-username/brokerage-firm.git
```

2. Navigate to the project directory
```bash
cd brokerage-firm
```

3. Build the project
```bash
mvn clean install
```

4. Run the application
```bash
mvn spring-boot:run
```

## API Endpoints

### Orders
- POST /api/orders/create - Create new order
- GET /api/orders/list - List orders
- POST /api/orders/cancel/{orderId} - Cancel order

### Assets
- GET /api/assets/list - List assets
- POST /api/assets/deposit - Deposit money
- POST /api/assets/withdraw - Withdraw money

## Database

The application uses H2 in-memory database. You can access the H2 console at:
`http://localhost:8080/h2-console`

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
