package com.newcord.articleservice.videoEdit;

import com.newcord.articleservice.clip.Clip;
import java.util.List;
import lombok.Builder;

@Builder
public class VideoEdit {
    private String id;
    private List<Clip> clip_list;

}
