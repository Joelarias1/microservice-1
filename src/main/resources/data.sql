-- Insertar usuario administrador
INSERT INTO users (username, email, password, role, status) VALUES ('admin', 'admin@example.com', 'admin123', 'ADMIN', 'ACTIVE');

-- Insertar moderador
INSERT INTO users (username, email, password, role, status) VALUES ('moderator', 'moderator@example.com', 'mod123', 'MODERATOR', 'ACTIVE');

-- Insertar usuarios regulares
INSERT INTO users (username, email, password, role, status) VALUES ('juandeveloper', 'juan@example.com', 'juan123', 'USER', 'ACTIVE');
INSERT INTO users (username, email, password, role, status) VALUES ('mariafrontend', 'maria@example.com', 'maria123', 'USER', 'ACTIVE');
INSERT INTO users (username, email, password, role, status) VALUES ('carlosfullstack', 'carlos@example.com', 'carlos123', 'USER', 'ACTIVE');
INSERT INTO users (username, email, password, role, status) VALUES ('anabackend', 'ana@example.com', 'ana123', 'USER', 'ACTIVE');
INSERT INTO users (username, email, password, role, status) VALUES ('pedrodevops', 'pedro@example.com', 'pedro123', 'USER', 'ACTIVE');
INSERT INTO users (username, email, password, role, status) VALUES ('lauradata', 'laura@example.com', 'laura123', 'USER', 'ACTIVE');
INSERT INTO users (username, email, password, role, status) VALUES ('sergiocloud', 'sergio@example.com', 'sergio123', 'USER', 'ACTIVE');

-- Insertar usuario baneado
INSERT INTO users (username, email, password, role, status) VALUES ('banneduser', 'banned@example.com', 'banned123', 'USER', 'BANNED');

-- Insertar Categorías Iniciales
INSERT INTO categories (name, description) VALUES ('Tecnología', 'Discusiones sobre tecnología, programación y desarrollo');
INSERT INTO categories (name, description) VALUES ('Videojuegos', 'Todo sobre gaming, consolas y juegos');
INSERT INTO categories (name, description) VALUES ('Música', 'Discusiones sobre música, artistas y géneros');
INSERT INTO categories (name, description) VALUES ('Deportes', 'Conversaciones sobre deportes y eventos deportivos');
INSERT INTO categories (name, description) VALUES ('Cine y Series', 'Debates sobre películas, series y entretenimiento');

-- Insertar Posts de muestra
INSERT INTO posts (title, content, user_id, category_id, status, created_at) 
VALUES ('Guía definitiva para aprender Angular 18 en 2024', 
        'Angular 18 trae grandes mejoras en rendimiento y nuevas características que facilitan el desarrollo de aplicaciones modernas. En esta guía exploraremos los conceptos fundamentales, desde la configuración del entorno hasta técnicas avanzadas para optimizar tus aplicaciones. También veremos las mejores prácticas para trabajar con el nuevo sistema de señales y cómo aprovechar al máximo las funcionalidades standalone.', 
        3, 1, 'ACTIVE', CURRENT_TIMESTAMP - INTERVAL '7' DAY);

INSERT INTO posts (title, content, user_id, category_id, status, created_at) 
VALUES ('Comparativa: React vs Angular vs Vue en 2024', 
        '¿Cuál es el mejor framework frontend en 2024? Analizamos en profundidad las ventajas y desventajas de cada uno, considerando factores como rendimiento, curva de aprendizaje, soporte de la comunidad, y escalabilidad para proyectos de diferentes tamaños. También incluimos ejemplos de código y casos de uso donde cada framework brilla especialmente.', 
        4, 1, 'ACTIVE', CURRENT_TIMESTAMP - INTERVAL '14' DAY);

INSERT INTO posts (title, content, user_id, category_id, status, created_at) 
VALUES ('Implementando microservicios con Spring Boot y Docker', 
        'En este tutorial, mostramos paso a paso cómo diseñar e implementar una arquitectura de microservicios utilizando Spring Boot, Docker y Kubernetes. Aprenderás patrones de diseño comunes, estrategias de comunicación entre servicios, y técnicas para garantizar la resiliencia y escalabilidad de tu aplicación.', 
        5, 1, 'ACTIVE', CURRENT_TIMESTAMP - INTERVAL '3' DAY);

INSERT INTO posts (title, content, user_id, category_id, status, created_at) 
VALUES ('Mejores prácticas para CI/CD con GitHub Actions', 
        'GitHub Actions se ha convertido en una herramienta fundamental para la integración y despliegue continuo. En este post, compartimos estrategias avanzadas para configurar flujos de trabajo eficientes, automatizar pruebas, y optimizar el proceso de entrega de software. Incluye ejemplos reales y configuraciones que puedes adaptar a tus proyectos.', 
        7, 3, 'ACTIVE', CURRENT_TIMESTAMP - INTERVAL '5' DAY);

INSERT INTO posts (title, content, user_id, category_id, status, created_at) 
VALUES ('Introducción a LangChain: Construyendo aplicaciones con LLMs', 
        'LangChain es un framework que simplifica la creación de aplicaciones avanzadas con Modelos de Lenguaje de Gran Escala (LLMs). Este tutorial te guía a través de la configuración inicial, integración con diferentes modelos como GPT-4, y creación de cadenas complejas para aplicaciones de IA generativa con memoria y razonamiento.', 
        8, 1, 'ACTIVE', CURRENT_TIMESTAMP - INTERVAL '2' DAY);

INSERT INTO posts (title, content, user_id, category_id, status, created_at) 
VALUES ('Los mejores juegos de rol del 2024', 
        'Este año ha traído grandes lanzamientos para los amantes de los RPG. Desde remakes de clásicos hasta innovadoras propuestas independientes, analizamos los juegos de rol más destacados, sus sistemas de combate, narrativa y mundo abierto. Compartimos nuestras experiencias y recomendaciones para distintos tipos de jugadores.', 
        6, 2, 'ACTIVE', CURRENT_TIMESTAMP - INTERVAL '4' DAY);

INSERT INTO posts (title, content, user_id, category_id, status, created_at) 
VALUES ('Colecciones imprescindibles de cine de ciencia ficción', 
        'La ciencia ficción nos permite explorar futuros posibles y reflexionar sobre el impacto de la tecnología en la sociedad. Presentamos una selección de las sagas y películas más influyentes del género, analizando sus temas, efectos visuales y contribuciones a la cultura popular. Desde clásicos atemporales hasta producciones recientes.', 
        9, 5, 'ACTIVE', CURRENT_TIMESTAMP - INTERVAL '6' DAY);

-- Insertar algunos comentarios de muestra
INSERT INTO comments (content, user_id, post_id, status, created_at)
VALUES ('Excelente guía, me ha ayudado mucho a entender los nuevos conceptos de Angular 18. Especialmente útil la parte sobre señales.', 
        5, 1, 'ACTIVE', CURRENT_TIMESTAMP - INTERVAL '5' DAY);

INSERT INTO comments (content, user_id, post_id, status, created_at)
VALUES ('En mi experiencia, Vue es ideal para proyectos pequeños y medianos. React tiene mejor ecosistema para aplicaciones grandes.', 
        3, 2, 'ACTIVE', CURRENT_TIMESTAMP - INTERVAL '10' DAY);

INSERT INTO comments (content, user_id, post_id, status, created_at)
VALUES ('¿Podrías compartir un ejemplo de configuración óptima para Kubernetes en producción? Estoy teniendo problemas con la escalabilidad.', 
        6, 4, 'ACTIVE', CURRENT_TIMESTAMP - INTERVAL '3' DAY);

INSERT INTO comments (content, user_id, post_id, status, created_at)
VALUES ('He implementado LangChain en mi proyecto y ha reducido significativamente el tiempo de desarrollo. Muy recomendable para cualquier aplicación IA.', 
        7, 5, 'ACTIVE', CURRENT_TIMESTAMP - INTERVAL '1' DAY);

 