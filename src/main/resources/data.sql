-- Insertar Categorías Iniciales
INSERT INTO categories (name, description) VALUES ('Tecnología', 'Discusiones sobre tecnología, programación y desarrollo');
INSERT INTO categories (name, description) VALUES ('Videojuegos', 'Todo sobre gaming, consolas y juegos');
INSERT INTO categories (name, description) VALUES ('Música', 'Discusiones sobre música, artistas y géneros');
INSERT INTO categories (name, description) VALUES ('Deportes', 'Conversaciones sobre deportes y eventos deportivos');
INSERT INTO categories (name, description) VALUES ('Cine y Series', 'Debates sobre películas, series y entretenimiento');

-- Insertar Usuarios Iniciales
-- La contraseña para todos es '123456', hasheada con BCrypt.
INSERT INTO users (username, email, password, role, status) VALUES ('admin', 'admin@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'ADMIN', 'ACTIVE');
INSERT INTO users (username, email, password, role, status) VALUES ('moderator', 'moderator@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'MODERATOR', 'ACTIVE');
INSERT INTO users (username, email, password, role, status) VALUES ('user1', 'user1@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'USER', 'ACTIVE');
INSERT INTO users (username, email, password, role, status) VALUES ('user2', 'user2@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'USER', 'ACTIVE'); 