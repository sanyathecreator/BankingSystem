# Banking System

A console-based banking application built with Java that provides separate interfaces for customers and administrators to manage banking operations.

## Features

### Customer Features
- **Account Authentication**: Secure login system for customer accounts
- **Balance Checking**: View current account balance
- **Money Transfers**: Transfer funds to other accounts
- **Transaction History**: View colorized transaction history with detailed records

### Administrator Features
- **User Management**: View, search, and delete user accounts
- **Balance Management**: Modify customer account balances
- **Transaction Monitoring**: View all system transactions with logging capabilities
- **System Overview**: Display total system balance across all customer accounts

## Technology Stack
- **Language**: Java 21
- **Build Tool**: Gradle 9.1.0
- **Testing Framework**: JUnit

## Requirements

- Java Development Kit (JDK) 21 or higher
- Gradle (wrapper included)

## Installation

1. Clone the repository:
```bash
git clone https://github.com/sanyathecreator/BankingSystem.git
cd BankingSystem
```

2. Build the project:
```bash
./gradlew build
```

## Running the Application

Execute the application using Gradle:

```bash
./gradlew run
```

Or on Windows:

```cmd
gradlew.bat run
```

## Running Tests

Execute the test suite:

```bash
./gradlew test
```

### Customer Operations
1. Check your account balance
2. Transfer money to other accounts
3. View transaction history with color-coded display
4. Logout

### Administrator Operations
1. View all users in the system
2. Search for users by username
3. Modify customer account balances
4. Delete user accounts
5. View all transactions
6. Monitor total system balance
7. Logout

## Architecture

The application follows a layered architecture pattern:

- **Model Layer**: Defines the core domain entities (User, Customer, Admin, Transaction)
- **Repository Layer**: Handles data storage and retrieval operations
- **Service Layer**: Contains business logic and orchestrates operations
- **UI Layer**: Manages user interaction through console-based menus
- **Utility Layer**: Provides helper functions like console colorization
