package com.newcord.articleservice.domain.videoEdit.repository;

import com.newcord.articleservice.domain.videoEdit.entity.VideoEdit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoEditRepository extends MongoRepository<VideoEdit, String> {

}
