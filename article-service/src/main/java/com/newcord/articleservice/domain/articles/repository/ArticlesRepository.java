package com.newcord.articleservice.domain.articles.repository;

import com.newcord.articleservice.domain.articles.entity.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ArticlesRepository extends MongoRepository<Article, Long> {

}
