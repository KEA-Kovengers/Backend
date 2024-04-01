package com.newcord.articleservice.videoEdit.entity;

import com.newcord.articleservice.clip.entity.Clip;
import java.util.List;
import lombok.Builder;

@Builder
public class VideoEdit {
    private String id;
    private List<Clip> clip_list;

}
