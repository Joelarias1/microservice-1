# Sistema de Foros en Línea - Backend

## Descripción del Proyecto
Sistema de foros en línea desarrollado con Spring Boot, implementando una arquitectura de microservicios.

### Microservicios Implementados
1. **Gestión de Usuarios**
   - Control de usuarios (CRUD)
   - Autenticación y autorización
   - Gestión de roles

2. **Gestión de Foros y Comentarios**
   - Búsqueda de temas
   - Visualización de publicaciones
   - Creación y gestión de comentarios
   - Moderación de contenido

### Categorías de Foros
1. Tecnología
2. Videojuegos
3. Música
4. Deportes
5. Cine y Series

### Roles de Usuario
1. **Administrador**
   - Gestión completa de usuarios
   - Moderación de contenido
   - Gestión de categorías

2. **Usuario Regular**
   - Crear y editar sus publicaciones
   - Comentar en publicaciones
   - Visualizar contenido

## Tecnologías Utilizadas
- Spring Boot 3.x
- Oracle Database
- Spring Security
- Spring Data JPA
- Maven
- JUnit (pruebas unitarias)
- Postman (pruebas de API)

## Estructura de Base de Datos

### Tabla: Users
```sql
CREATE TABLE users (
    id NUMBER PRIMARY KEY,
    username VARCHAR2(50) UNIQUE NOT NULL,
    password VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    role VARCHAR2(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR2(20) DEFAULT 'ACTIVE'
);
```

### Tabla: Categories
```sql
CREATE TABLE categories (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(50) NOT NULL,
    description VARCHAR2(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Tabla: Posts
```sql
CREATE TABLE posts (
    id NUMBER PRIMARY KEY,
    title VARCHAR2(200) NOT NULL,
    content CLOB NOT NULL,
    user_id NUMBER REFERENCES users(id),
    category_id NUMBER REFERENCES categories(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR2(20) DEFAULT 'ACTIVE'
);
```

### Tabla: Comments
```sql
CREATE TABLE comments (
    id NUMBER PRIMARY KEY,
    content VARCHAR2(1000) NOT NULL,
    post_id NUMBER REFERENCES posts(id),
    user_id NUMBER REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR2(20) DEFAULT 'ACTIVE'
);
```

## Endpoints API

### Usuarios
- `POST /api/users` - Crear usuario
- `GET /api/users` - Listar usuarios
- `GET /api/users/{id}` - Obtener usuario por ID
- `PUT /api/users/{id}` - Actualizar usuario
- `DELETE /api/users/{id}` - Eliminar usuario

### Foros
- `POST /api/posts` - Crear publicación
- `GET /api/posts` - Listar publicaciones
- `GET /api/posts/{id}` - Obtener publicación por ID
- `PUT /api/posts/{id}` - Actualizar publicación
- `DELETE /api/posts/{id}` - Eliminar publicación
- `GET /api/categories` - Listar categorías

### Comentarios
- `POST /api/comments` - Crear comentario
- `GET /api/posts/{id}/comments` - Listar comentarios de una publicación
- `PUT /api/comments/{id}` - Actualizar comentario
- `DELETE /api/comments/{id}` - Eliminar comentario

## Configuración del Proyecto
1. Clonar el repositorio
2. Configurar la base de datos Oracle en `application.properties`
3. Ejecutar los scripts de base de datos
4. Compilar el proyecto con Maven
5. Ejecutar los microservicios

## Pruebas con Postman
Se incluirá una colección de Postman con todos los endpoints configurados para pruebas. 
