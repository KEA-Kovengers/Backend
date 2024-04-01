package com.newcord.articleservice.block.repository;

import com.newcord.articleservice.block.entity.Block;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlockRepository extends MongoRepository<Block, String> {

}
