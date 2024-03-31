package com.newcord.articleservice.articles.repository;

import com.newcord.articleservice.articles.Articles;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticlesRepository extends MongoRepository<Articles, String> {

}
