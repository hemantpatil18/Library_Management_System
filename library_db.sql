
CREATE DATABASE IF NOT EXISTS library_db;

USE library_db;

CREATE TABLE IF NOT EXISTS books (
                                     book_id INT PRIMARY KEY,
                                     title VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    quantity INT NOT NULL
    );

CREATE TABLE IF NOT EXISTS students (
                                        student_id INT PRIMARY KEY,
                                        name VARCHAR(100) NOT NULL,
    contact VARCHAR(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS issued_books (
                                            issue_id INT PRIMARY KEY AUTO_INCREMENT,
                                            book_id INT NOT NULL,
                                            student_id INT NOT NULL,
                                            issue_date DATE NOT NULL,
                                            return_date DATE DEFAULT NULL,
                                            status VARCHAR(20) NOT NULL DEFAULT 'ISSUED',
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id)
    );
