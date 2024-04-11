package com.newcord.articleservice.domain.clip.entity;

import com.newcord.articleservice.global.common.BaseMongoTimeEntity;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//TODO: Clip 내부 변수 기획 필요
@Document
@Builder
public class Clip extends BaseMongoTimeEntity {
    @Id
    private String id;

}
