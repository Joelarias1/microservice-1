-- Insertar Categorías Iniciales
INSERT INTO categories (name, description) VALUES ('Tecnología', 'Discusiones sobre tecnología, programación y desarrollo');
INSERT INTO categories (name, description) VALUES ('Videojuegos', 'Todo sobre gaming, consolas y juegos');
INSERT INTO categories (name, description) VALUES ('Música', 'Discusiones sobre música, artistas y géneros');
INSERT INTO categories (name, description) VALUES ('Deportes', 'Conversaciones sobre deportes y eventos deportivos');
INSERT INTO categories (name, description) VALUES ('Cine y Series', 'Debates sobre películas, series y entretenimiento');

-- Insertar Usuarios Iniciales
-- ¡ATENCIÓN! Las contraseñas están en texto plano. Considera usar hashes en una aplicación real.
INSERT INTO users (username, email, password, role, status) VALUES ('admin', 'admin@example.com', '123456', 'ADMIN', 'ACTIVE');
INSERT INTO users (username, email, password, role, status) VALUES ('moderator', 'moderator@example.com', '123456', 'MODERATOR', 'ACTIVE');
INSERT INTO users (username, email, password, role, status) VALUES ('user1', 'user1@example.com', '123456', 'USER', 'ACTIVE');
INSERT INTO users (username, email, password, role, status) VALUES ('user2', 'user2@example.com', '123456', 'USER', 'ACTIVE'); 