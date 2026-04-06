-- INSERTAR EQUIPOS (Teams)
INSERT INTO teams (name) VALUES ('Real Madrid');
INSERT INTO teams (name) VALUES ('FC Barcelona');
INSERT INTO teams (name) VALUES ('Real Betis');
INSERT INTO teams (name) VALUES ('Sevilla FC');

-- INSERTAR JUGADORES (Players)
-- Asumiendo que los IDs de los equipos generados son 1, 2, 3 y 4
INSERT INTO players (name, number, team_id) VALUES ('Vinicius Jr', 7, 1);
INSERT INTO players (name, number, team_id) VALUES ('Lamine Yamal', 19, 2);
INSERT INTO players (name, number, team_id) VALUES ('Isco Alarcon', 22, 3);
INSERT INTO players (name, number, team_id) VALUES ('Isaac Romero', 20, 4);
INSERT INTO players (name, number, team_id) VALUES ('Jude Bellingham', 5, 1);
INSERT INTO players (name, number, team_id) VALUES ('Robert Lewandowski', 9, 2);

CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
);

-- Insertamos un usuario de prueba (la contraseña es 'password' en BCrypt)
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xdq1YVPHZi67PYlG', 1);
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');