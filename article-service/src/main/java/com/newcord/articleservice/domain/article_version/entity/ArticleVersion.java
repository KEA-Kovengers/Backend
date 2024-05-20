package com.newcord.articleservice.domain.article_version.entity;

import com.newcord.articleservice.domain.block.entity.BlockUpdatedBy;
import jakarta.persistence.Entity;
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
    private Long id;
    private List<Version> versions;
}

@Builder
@Getter
class Version{
    private String timestamp;           // 첫 Operation 시간 (버전을 나누기 위함)
    private List<Operation> operations;
}

@Builder
@Getter
class Operation{
    private OperationType operationType;            //Operation Type
    private ObjectId id;                            //수정한 블럭 ID
    private String timestamp;                       //수정 요청한 시간(클라이언트 시간)
    private long position;                          //블럭 내에서 위치
    private String content;
    private BlockUpdatedBy updated_by;
}
