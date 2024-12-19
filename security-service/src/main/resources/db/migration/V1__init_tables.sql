DROP TABLE IF EXISTS user;

CREATE TABLE user(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) ,
    password VARCHAR(255) ,
    email VARCHAR(255) ,
    role ENUM('ROLE_USER','ROLE_MANAGER','ROEL_ADMIN') NOT NULL DEFAULT 'ROLE_USER' ,
    provider varchar(255) ,
    provider_id varchar(255) ,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
