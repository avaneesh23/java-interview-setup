CREATE TABLE movies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    release_year BIGINT
);

INSERT INTO movies VALUES (1, 'Gladiator', 1997);
INSERT INTO movies VALUES (2, 'The Beautiful Mind', 2001);
INSERT INTO movies VALUES (3, 'The Departed', 2005);