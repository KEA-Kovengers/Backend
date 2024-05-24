package com.newcord.noticeservice.domain.repository;

import com.newcord.noticeservice.domain.entity.Notices;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoticesRepository extends MongoRepository<Notices, ObjectId> {
}
