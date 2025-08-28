CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(100) PRIMARY KEY DEFAULT (uuid()),
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE,
    phone VARCHAR(30),
    address VARCHAR(200),
    email VARCHAR(100) UNIQUE,
    salary DOUBLE
);