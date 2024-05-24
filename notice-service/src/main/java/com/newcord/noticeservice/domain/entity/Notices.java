package com.newcord.noticeservice.domain.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Notices implements Serializable {

    @Id
    private ObjectId id;
    private Long user_id;
    private String body;
    private NoticeType type;
    private StatusType status;
    @CreatedDate
    private LocalDateTime created_at;

    @Builder
    public Notices(Long user_id, String body, NoticeType type) {
        this.user_id = user_id;
        this.body = body;
        this.type = type;
    }
}
