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