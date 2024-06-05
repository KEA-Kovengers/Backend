package com.newcord.articleservice.global.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseJPATimeEntity implements Serializable {

    // Entity가 생성되어 저장될 때 시간이 자동 저장됩니다.
    @CreatedDate
    @Column(updatable = false, columnDefinition = "TEXT")
    @JsonFormat()
    private LocalDateTime created_at;

    // 조회한 Entity 값을 변경할 때 시간이 자동 저장됩니다.
    @LastModifiedDate
    @Column(updatable = false, columnDefinition = "TEXT")
    private LocalDateTime updated_at;

    protected void update(){
        updated_at = LocalDateTime.now();
    }
}
