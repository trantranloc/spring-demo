package com.spring.spring_demo;

import com.spring.spring_demo.model.Role;
import com.spring.spring_demo.model.User;
import com.spring.spring_demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@SpringBootApplication
public class SpringDemoApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public SpringDemoApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoApplication.class, args);
	}
	@Override
	public void run(String... args) {
		if (userRepository.findByUsername("admin").isEmpty()) {
			User admin = new User();
			admin.setUsername("admin");
			admin.setName("Admin");
			admin.setEmail("admin@gmail.com");
			admin.setPhone("0987654321");
			admin.setPassword(passwordEncoder.encode("admin123"));
			admin.setRoles(Collections.singleton(new Role("ROLE_ADMIN")));

			userRepository.save(admin);
			System.out.println("User ADMIN đã được tạo!");
		}
	}
}
