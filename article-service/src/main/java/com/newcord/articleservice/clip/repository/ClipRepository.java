package com.newcord.articleservice.clip.repository;

import com.newcord.articleservice.clip.entity.Clip;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClipRepository extends MongoRepository<Clip, String> {

}
