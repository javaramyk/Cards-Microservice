package com.example.microservice.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info= @Info(
				title="Cards Microservice REST API Documentation",
				description="Spring Boot Cards Microservice REST API DOcumentation",
				version="v1",
				contact=@Contact(
						name="Ramya Karanam",
						email="ramyakaranam123@gmail.com",
						url="https://www.java.com"
						),
				license=@License(
						name="Apache 2.0",
						url="https://www.java.com"
						)
				),
		externalDocs=@ExternalDocumentation(
				description="Spring boot cards microservcie REST API DOcumentation",
				url="https://www.java.com"
				)
		)
public class CardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardApplication.class, args);
	}

}
