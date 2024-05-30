package com.newcord.articleservice.domain.block.repository;

import com.newcord.articleservice.domain.articles.entity.Article;
import com.newcord.articleservice.domain.block.entity.Block;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BlockRepository extends MongoRepository<Block, ObjectId> {
    @Query("{'created_by.creator_id': ?0}")
    List<Block> findByCreatedByCreatorId(Long creator_id);



//    @Query("{'created_by.creator_id': ?0,'_id': ?1}")
//    Block findbyblockIdAndCreatorId(Long creator_id,String id);

}
