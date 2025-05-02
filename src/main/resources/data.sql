-- Insertar usuario administrador
INSERT INTO users (username, email, password, role, status) VALUES ('admin', 'admin@example.com', 'admin123', 'ADMIN', 'ACTIVE');
-- Insertar Categorías Iniciales
INSERT INTO categories (name, description) VALUES ('Tecnología', 'Discusiones sobre tecnología, programación y desarrollo');
INSERT INTO categories (name, description) VALUES ('Videojuegos', 'Todo sobre gaming, consolas y juegos');
INSERT INTO categories (name, description) VALUES ('Música', 'Discusiones sobre música, artistas y géneros');
INSERT INTO categories (name, description) VALUES ('Deportes', 'Conversaciones sobre deportes y eventos deportivos');
INSERT INTO categories (name, description) VALUES ('Cine y Series', 'Debates sobre películas, series y entretenimiento');

 