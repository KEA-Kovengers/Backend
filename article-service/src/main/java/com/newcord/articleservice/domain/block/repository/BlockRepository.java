package com.newcord.articleservice.domain.block.repository;

import com.newcord.articleservice.domain.block.entity.Block;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BlockRepository extends MongoRepository<Block, ObjectId> {
    @Query("{'created_by.creator_id': ?0}")
    List<Block> findByCreatedByCreatorId(Long creator_id);


}
