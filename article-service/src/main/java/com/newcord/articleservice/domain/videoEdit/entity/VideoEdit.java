package com.newcord.articleservice.domain.videoEdit.entity;

import com.newcord.articleservice.global.common.BaseMongoTimeEntity;
import jakarta.persistence.Id;
import java.util.List;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
public class VideoEdit extends BaseMongoTimeEntity {
    @Id
    private String id;
    private List<String> clip_list;

}
