package com.newcord.articleservice.domain.article_version.entity;

import com.newcord.articleservice.domain.block.entity.BlockUpdatedBy;
import jakarta.persistence.Id;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

// 버전은 {version index}.{operation index} 형태
@Document
@Builder
@Getter
public class ArticleVersion {
    @Id
    private Long id;                    //게시글 아이디
    private List<Version> versions;

}

