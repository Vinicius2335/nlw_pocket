package com.github.vinicius2335.back;

import com.github.vinicius2335.back.core.utils.SeedDatabase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocketApplication.class, args);
	}

	@Bean
	CommandLineRunner start(SeedDatabase seedDatabase){
		return args -> seedDatabase.execute();
	}

}
