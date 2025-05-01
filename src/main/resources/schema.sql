-- Eliminar tablas si existen (puede fallar si no existen, se ignora con continue-on-error=true)
DROP TABLE comments;
DROP TABLE posts;
DROP TABLE categories;
DROP TABLE users;

-- Crear tabla users
CREATE TABLE users (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR2(50) UNIQUE NOT NULL,
    password VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    role VARCHAR2(20) NOT NULL,
    status VARCHAR2(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear tabla categories
CREATE TABLE categories (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    description VARCHAR2(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear tabla posts
CREATE TABLE posts (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR2(200) NOT NULL,
    content CLOB NOT NULL,
    user_id NUMBER NOT NULL,
    category_id NUMBER NOT NULL,
    status VARCHAR2(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_post_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_post_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Crear tabla comments
CREATE TABLE comments (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    content CLOB NOT NULL,
    user_id NUMBER NOT NULL,
    post_id NUMBER NOT NULL,
    status VARCHAR2(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_comment_post FOREIGN KEY (post_id) REFERENCES posts(id)
); 