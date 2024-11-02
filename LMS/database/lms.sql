CREATE DATABASE lms;
USE lms;

-- Table for storing user roles
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(10) NOT NULL UNIQUE
);
INSERT INTO roles (role_name) VALUES ('admin'), ('user');

-- Authors table for storing book authors separately
CREATE TABLE authors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Users table with normalized role reference
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(80) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Books table 
CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    author_id INT NOT NULL,
    isbn VARCHAR(50) UNIQUE NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES authors(id)
);

-- Borrows table with foreign key relationships to users and books tables
CREATE TABLE borrows (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);
SELECT * FROM roles;
SELECT * FROM users;
SELECT * FROM books;
SELECT * FROM borrows;
