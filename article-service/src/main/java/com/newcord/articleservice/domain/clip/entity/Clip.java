package com.newcord.articleservice.domain.clip.entity;

import com.newcord.articleservice.global.common.BaseTimeEntity;
import lombok.Builder;

//TODO: Clip 내부 변수 기획 필요
@Builder
public class Clip extends BaseTimeEntity {
    private String id;

}
