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
    private String id;
    private Long user_id; // 알림 받는 유저 아이디
    private Long from_id; // 알림 발생시킨 유저 아이디
    private Long post_id; // 알림이 발생된 게시글 아이디
    private Long comment_id; // 알림이 발생된 댓글 아이디
    private NoticeType type; // 알림 종류
    private StatusType status; // 알림 확인 여부
    @CreatedDate
    private LocalDateTime created_at;
}
