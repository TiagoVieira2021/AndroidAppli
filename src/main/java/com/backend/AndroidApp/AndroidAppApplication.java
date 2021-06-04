package com.backend.AndroidApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

public class AndroidAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AndroidAppApplication.class, args);
	}

}
