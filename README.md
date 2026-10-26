# Library Management System

## Project Overview
The Library Management System is a Java-based application developed to manage books, students, and book issue and return operations. It uses MySQL to store and manage data permanently.

## Technologies Used
- Java
- Object-Oriented Programming (OOP)
- JDBC
- MySQL
- Maven
- IntelliJ IDEA

## Features

### Book Management
- Add a new book
- View all books
- Search for a book
- Update book details
- Delete a book
- Check book availability

### Student Management
- Add a student
- View all students
- Search for a student
- Update student details
- Delete a student

### Issue and Return Management
- Issue a book to a student
- Return an issued book
- Track issued books
- View issue and return status
- Automatically update book quantity when issuing or returning books

## Database
Database name: `library_db`

Tables:
- `books`
- `students`
- `issued_books`

## Setup Instructions

1. Install the Java JDK and MySQL.
2. Open MySQL and run the `library_db.sql` script.
3. Open the project in IntelliJ IDEA.
4. Check the MySQL connection settings in `DBConnection.java`.
5. Ensure the MySQL Connector/J dependency is configured in `pom.xml`.
6. Run `Main.java`.
7. Follow the console menu to manage books, students, and issue/return operations.

## Project Structure

- `Book.java` – Book model class
- `Student.java` – Student model class
- `BookDoA.java` – Book database operations
- `StudentDAO.java` – Student database operations
- `IssueBookDAO.java` – Book issue and return operations
- `DBConnection.java` – MySQL database connection
- `Main.java` – Main menu and application execution
- `library_db.sql` – Database creation script

## Conclusion
This project demonstrates how Java, OOP, JDBC, and MySQL can be used to develop a simple Library Management System.