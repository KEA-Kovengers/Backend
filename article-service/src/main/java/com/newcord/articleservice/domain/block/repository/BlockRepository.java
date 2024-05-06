package com.newcord.articleservice.domain.block.repository;

import com.newcord.articleservice.domain.block.entity.Block;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlockRepository extends MongoRepository<Block, ObjectId> {

}
