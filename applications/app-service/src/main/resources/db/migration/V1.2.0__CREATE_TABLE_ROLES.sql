ALTER TABLE users ADD COLUMN identification_number VARCHAR(50) UNIQUE;
ALTER TABLE users ADD COLUMN password VARCHAR(255) NOT NULL;
ALTER TABLE users ADD COLUMN role_id INTEGER;

CREATE TABLE IF NOT EXISTS roles (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200)
);

ALTER TABLE users ADD CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id);

INSERT INTO roles (id, name, description) VALUES
    (1, 'ADMIN', 'Administrator with full access'),
    (2, 'CUSTOMER', 'Regular customer with standard access'),
    (3, 'MANAGER', 'Manager with elevated privileges'),
    (5, 'ADVISOR', 'Advisor with special permissions');
