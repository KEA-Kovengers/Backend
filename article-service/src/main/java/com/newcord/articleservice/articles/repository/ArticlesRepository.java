package com.newcord.articleservice.articles.repository;

import com.newcord.articleservice.articles.entity.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticlesRepository extends MongoRepository<Article, String> {

}
