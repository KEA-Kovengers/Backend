package com.newcord.articleservice.domain.clip.repository;

import com.newcord.articleservice.domain.clip.entity.Clip;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClipRepository extends MongoRepository<Clip, String> {

}
