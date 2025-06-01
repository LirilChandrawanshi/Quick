package com.example.Quick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.Quick.Repository")
public class QuickApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickApplication.class, args);
	}

}
