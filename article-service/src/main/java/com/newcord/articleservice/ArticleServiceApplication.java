package com.newcord.articleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.newcord.articleservice")
@EnableDiscoveryClient
public class ArticleServiceApplication {
	//Test comment
	public static void main(String[] args) {
		SpringApplication.run(ArticleServiceApplication.class, args);
	}

}
