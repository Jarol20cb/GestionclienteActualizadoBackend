package com.gestioncliente.gestionclientenew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GestionClienteNewApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionClienteNewApplication.class, args);
	}

}
