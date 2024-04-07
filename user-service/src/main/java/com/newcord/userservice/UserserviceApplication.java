package com.newcord.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class UserserviceApplication {
	//Test
	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

}
