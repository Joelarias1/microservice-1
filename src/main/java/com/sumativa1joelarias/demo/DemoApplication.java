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


}
