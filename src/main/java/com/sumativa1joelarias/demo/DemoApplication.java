package com.sumativa1joelarias.demo;

import com.sumativa1joelarias.demo.microservices.forums.model.Category;
import com.sumativa1joelarias.demo.microservices.forums.repository.CategoryRepository;
import com.sumativa1joelarias.demo.microservices.users.model.User;
import com.sumativa1joelarias.demo.microservices.users.repository.UserRepository;
import com.sumativa1joelarias.demo.microservices.users.enums.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/* // Comentado: La inicialización de datos se moverá a data.sql
	@Bean
	public CommandLineRunner initData(CategoryRepository categoryRepository, UserRepository userRepository) {
		return args -> {
			// Solo insertar categorías si no hay
			if (categoryRepository.count() == 0) {
				// Crear categorías
				createCategory(categoryRepository, "Tecnología", "Discusiones sobre tecnología, programación y desarrollo");
				createCategory(categoryRepository, "Videojuegos", "Todo sobre gaming, consolas y juegos");
				createCategory(categoryRepository, "Música", "Discusiones sobre música, artistas y géneros");
				createCategory(categoryRepository, "Deportes", "Conversaciones sobre deportes y eventos deportivos");
				createCategory(categoryRepository, "Cine y Series", "Debates sobre películas, series y entretenimiento");
			}

			// Solo insertar usuarios si no hay
			if (userRepository.count() == 0) {
				// Crear usuarios por defecto
				createUser(userRepository, "admin", "admin@example.com", "123456", UserRole.ADMIN);
				createUser(userRepository, "moderator", "moderator@example.com", "123456", UserRole.MODERATOR);
				createUser(userRepository, "user1", "user1@example.com", "123456", UserRole.USER);
				createUser(userRepository, "user2", "user2@example.com", "123456", UserRole.USER);
			}
		};
	}

	private void createCategory(CategoryRepository repository, String name, String description) {
		Category category = new Category();
		category.setName(name);
		category.setDescription(description);
		repository.save(category);
	}

	private void createUser(UserRepository repository, String username, String email, String password, UserRole role) {
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password); // ¡Considerar hashear la contraseña aquí!
		user.setRole(role);
		user.setStatus("ACTIVE");
		repository.save(user);
	}
	*/

}
