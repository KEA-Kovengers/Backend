package com.newcord.articleservice.domain.article_version.repository;

import com.newcord.articleservice.domain.article_version.entity.ArticleVersion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleVersionRepository extends MongoRepository<ArticleVersion, Long> {

}
