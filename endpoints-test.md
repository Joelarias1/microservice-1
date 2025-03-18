# Guía de Pruebas de Endpoints - Foro

Esta guía proporciona ejemplos de cómo probar todos los endpoints del sistema de foros usando cURL. También puedes usar estos ejemplos en Postman.

## Categorías

### 1. Crear Categoría
```bash
curl -X POST http://localhost:8080/api/categories \
-H "Content-Type: application/json" \
-d '{
    "name": "Tecnología",
    "description": "Discusiones sobre tecnología y programación"
}'
```

### 2. Obtener Todas las Categorías
```bash
curl http://localhost:8080/api/categories
```

### 3. Obtener Categoría por ID
```bash
curl http://localhost:8080/api/categories/1
```

### 4. Actualizar Categoría
```bash
curl -X PUT http://localhost:8080/api/categories/1 \
-H "Content-Type: application/json" \
-d '{
    "name": "Tecnología y Programación",
    "description": "Discusiones sobre tecnología, programación y desarrollo de software"
}'
```

### 5. Eliminar Categoría
```bash
curl -X DELETE http://localhost:8080/api/categories/1
```

## Posts

### 1. Crear Post
```bash
curl -X POST http://localhost:8080/api/posts \
-H "Content-Type: application/json" \
-d '{
    "title": "Introducción a Spring Boot",
    "content": "Spring Boot es un framework que facilita el desarrollo de aplicaciones...",
    "userId": 1,
    "categoryId": 1,
    "status": "ACTIVE"
}'
```

### 2. Obtener Todos los Posts
```bash
curl http://localhost:8080/api/posts
```

### 3. Obtener Post por ID
```bash
curl http://localhost:8080/api/posts/1
```

### 4. Actualizar Post
```bash
curl -X PUT http://localhost:8080/api/posts/1 \
-H "Content-Type: application/json" \
-d '{
    "title": "Guía Completa de Spring Boot",
    "content": "Spring Boot es un framework que facilita el desarrollo...",
    "status": "ACTIVE"
}'
```

### 5. Eliminar Post
```bash
curl -X DELETE http://localhost:8080/api/posts/1
```

### 6. Obtener Posts por Categoría
```bash
curl http://localhost:8080/api/posts/category/1
```

### 7. Obtener Posts por Usuario
```bash
curl http://localhost:8080/api/posts/user/1
```

## Comentarios

### 1. Crear Comentario
```bash
curl -X POST http://localhost:8080/api/comments \
-H "Content-Type: application/json" \
-d '{
    "content": "Excelente publicación, muy informativa",
    "postId": 1,
    "userId": 1,
    "status": "ACTIVE"
}'
```

### 2. Obtener Comentarios por Post
```bash
curl http://localhost:8080/api/comments/post/1
```

### 3. Obtener Comentarios por Usuario
```bash
curl http://localhost:8080/api/comments/user/1
```

### 4. Actualizar Comentario
```bash
curl -X PUT http://localhost:8080/api/comments/1 \
-H "Content-Type: application/json" \
-d '{
    "content": "Excelente publicación, muy útil para principiantes",
    "status": "ACTIVE"
}'
```

### 5. Eliminar Comentario
```bash
curl -X DELETE http://localhost:8080/api/comments/1
```

## Ejemplos de Respuestas

### Respuesta Exitosa
```json
{
    "message": "Operación realizada exitosamente",
    "success": true,
    "data": {
        "id": 1,
        "name": "Tecnología",
        "description": "Discusiones sobre tecnología y programación",
        "createdAt": "2024-03-14T12:00:00"
    }
}
```

### Respuesta de Error
```json
{
    "message": "Recurso no encontrado",
    "success": false,
    "data": null
}
```

## Notas Importantes
1. Asegúrate de que el servidor esté corriendo en `localhost:8080`
2. Todos los endpoints requieren que el Content-Type sea `application/json` para las peticiones POST y PUT
3. Los IDs en los ejemplos (1) deben ser reemplazados por IDs válidos de tu base de datos
4. El campo `status` acepta los valores: "ACTIVE" o "INACTIVE"
5. Las fechas se manejan automáticamente por el sistema 