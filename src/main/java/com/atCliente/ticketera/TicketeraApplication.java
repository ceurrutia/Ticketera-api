package com.atCliente.ticketera;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketeraApplication {

	public static void main(String[] args) {

		// Cargar .env
		Dotenv dotenv = Dotenv.load();

		// Pasar variables a Spring
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USER", dotenv.get("DB_USER"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		SpringApplication.run(TicketeraApplication.class, args);
	}

}
