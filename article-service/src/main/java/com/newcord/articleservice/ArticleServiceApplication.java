package com.newcord.articleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableMongoRepositories(basePackages = "com.newcord.articleservice")
public class ArticleServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ArticleServiceApplication.class, args);
	}

}
