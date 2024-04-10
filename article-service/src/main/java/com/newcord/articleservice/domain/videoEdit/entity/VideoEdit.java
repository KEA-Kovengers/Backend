package com.newcord.articleservice.domain.videoEdit.entity;

import com.newcord.articleservice.domain.clip.entity.Clip;
import com.newcord.articleservice.global.common.BaseTimeEntity;
import java.util.List;
import lombok.Builder;

@Builder
public class VideoEdit extends BaseTimeEntity {
    private String id;
    private List<Clip> clip_list;

}
