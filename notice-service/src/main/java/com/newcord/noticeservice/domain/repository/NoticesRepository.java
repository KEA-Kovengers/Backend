package com.newcord.noticeservice.domain.repository;

import com.newcord.noticeservice.domain.entity.Notices;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;


public interface NoticesRepository extends MongoRepository<Notices, ObjectId> {
    @Query("{ 'user_id' : ?0 }")
    List<Notices> findByUser_id(Long userId);

}
