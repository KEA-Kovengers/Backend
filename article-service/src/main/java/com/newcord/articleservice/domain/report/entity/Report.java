package com.newcord.articleservice.domain.report.entity;

import com.newcord.articleservice.domain.report.type.ReportType;
import com.newcord.articleservice.global.common.BaseJPATimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report extends BaseJPATimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //신고 댓글 혹은 게시글의 아이디
    private Long contentID;
    private Long userID;
    private String body;

    // 댓글이면 1, 게시글이면 2
    private ReportType type;
}
