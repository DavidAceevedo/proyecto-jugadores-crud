-- 1. CREAR TABLAS SI NO EXISTEN
CREATE TABLE IF NOT EXISTS users (
                                     username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL
    );

CREATE TABLE IF NOT EXISTS authorities (
                                           username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
    );

-- 2. LIMPIAR DATOS ANTERIORES
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE players;
TRUNCATE TABLE teams;
TRUNCATE TABLE authorities;
DELETE FROM users WHERE username = 'admin';
SET FOREIGN_KEY_CHECKS = 1;

-- 3. INSERTAR EQUIPOS
INSERT INTO teams (id, name) VALUES (1, 'Real Madrid');
INSERT INTO teams (id, name) VALUES (2, 'FC Barcelona');
INSERT INTO teams (id, name) VALUES (3, 'Real Betis');
INSERT INTO teams (id, name) VALUES (4, 'Sevilla FC');

-- 4. INSERTAR JUGADORES
INSERT INTO players (name, number, team_id) VALUES ('Vinicius Jr', 7, 1);
INSERT INTO players (name, number, team_id) VALUES ('Lamine Yamal', 19, 2);
INSERT INTO players (name, number, team_id) VALUES ('Isco Alarcon', 22, 3);
INSERT INTO players (name, number, team_id) VALUES ('Isaac Romero', 20, 4);
INSERT INTO players (name, number, team_id) VALUES ('Jude Bellingham', 5, 1);
INSERT INTO players (name, number, team_id) VALUES ('Robert Lewandowski', 9, 2);

-- 5. INSERTAR USUARIO ADMIN (Contraseña: password)
-- Fíjate que el ID es 1 y el ROLE se pone directamente aquí
INSERT INTO users (id, username, password, role, enabled)
VALUES (1, 'admin', '$2a$12$XTgOAG7cLaOx9U2AzkuWl.APQijl1cb2RKgVS5M0dxf0HMV4T4SJm', 'ROLE_ADMIN', 1);

INSERT INTO authorities (username, authority)
VALUES ('admin', 'ROLE_ADMIN');