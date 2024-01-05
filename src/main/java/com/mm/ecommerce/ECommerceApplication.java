package com.mm.ecommerce;

import com.mm.ecommerce.domain.Consumer;
import com.mm.ecommerce.domain.Merchant;
import com.mm.ecommerce.domain.SystemAdmin;
import com.mm.ecommerce.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}
	@Bean
	public CommandLineRunner loadData(UserRepository userRepository, PasswordEncoder encoder){
		SystemAdmin systemAdmin = new SystemAdmin();
		systemAdmin.setEmail("systemAdmin@gmail.com");
		systemAdmin.setPassword(encoder.encode("test"));

		Consumer consumer = new Consumer();
		consumer.setEmail("consumer@gmail.com");
		consumer.setPassword(encoder.encode("test"));

		Merchant merchant = new Merchant();
		merchant.setEmail("merchant@gmail.com");
		merchant.setPassword(encoder.encode("test"));

		return args -> {
			userRepository.save(systemAdmin);
			userRepository.save(consumer);
			userRepository.save(merchant);
		};
	}
}
