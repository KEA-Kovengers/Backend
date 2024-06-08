package com.newcord.articleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableMongoAuditing
@EnableScheduling
@EnableMongoRepositories(basePackages = "com.newcord.articleservice")
@EnableCaching
//@EnableDiscoveryClient
public class ArticleServiceApplication {
	//Test comment
	public static void main(String[] args) {
		SpringApplication.run(ArticleServiceApplication.class, args);
	}

}
