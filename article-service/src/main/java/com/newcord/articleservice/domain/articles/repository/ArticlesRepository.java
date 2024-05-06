package com.newcord.articleservice.domain.articles.repository;

import com.newcord.articleservice.domain.articles.entity.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticlesRepository extends MongoRepository<Article, Long> {

}
