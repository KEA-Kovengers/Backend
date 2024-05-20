package com.newcord.articleservice.domain.article_version.repository;

import com.newcord.articleservice.domain.article_version.entity.ArticleVersion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleVersionRepository extends MongoRepository<ArticleVersion, Long> {

}
