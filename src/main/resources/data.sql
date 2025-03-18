-- Insertar usuarios con diferentes roles
INSERT INTO users (username, password, email, role, status) 
VALUES ('admin', '123456', 'admin@example.com', 'ADMIN', 'ACTIVE');

INSERT INTO users (username, password, email, role, status) 
VALUES ('moderator', '123456', 'moderator@example.com', 'MODERATOR', 'ACTIVE');

INSERT INTO users (username, password, email, role, status) 
VALUES ('user1', '123456', 'user1@example.com', 'USER', 'ACTIVE');

INSERT INTO users (username, password, email, role, status) 
VALUES ('user2', '123456', 'user2@example.com', 'USER', 'ACTIVE');

-- Insertar categorías base
INSERT INTO categories (name, description) 
VALUES ('Tecnología', 'Discusiones sobre tecnología, programación y desarrollo');

INSERT INTO categories (name, description) 
VALUES ('Videojuegos', 'Todo sobre gaming, consolas y juegos');

INSERT INTO categories (name, description) 
VALUES ('Música', 'Discusiones sobre música, artistas y géneros');

INSERT INTO categories (name, description) 
VALUES ('Deportes', 'Conversaciones sobre deportes y eventos deportivos');

INSERT INTO categories (name, description) 
VALUES ('Cine y Series', 'Debates sobre películas, series y entretenimiento'); 