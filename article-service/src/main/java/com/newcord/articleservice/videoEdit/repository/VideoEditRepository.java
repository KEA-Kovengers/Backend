package com.newcord.articleservice.videoEdit.repository;

import com.newcord.articleservice.videoEdit.entity.VideoEdit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoEditRepository extends MongoRepository<VideoEdit, String> {

}
