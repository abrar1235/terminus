CREATE TABLE users(
	id VARCHAR(255) PRIMARY KEY,
	name VARCHAR(150) NOT NULL,
	email VARCHAR(150) UNIQUE,
	profession VARCHAR(100),
	password VARCHAR(255) NOT NULL
);