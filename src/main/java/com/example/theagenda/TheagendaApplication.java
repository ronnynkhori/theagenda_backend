package com.example.theagenda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TheagendaApplication {
	public static void main(String[] args) {
		SpringApplication.run(TheagendaApplication.class, args);
	}

}
