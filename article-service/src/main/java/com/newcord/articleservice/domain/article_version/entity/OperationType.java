package com.newcord.articleservice.domain.article_version.entity;

// 블럭 삽입, 삭제 텍스트 삽입, 삭제, 태그 변경(마크다운 태그)
public enum OperationType {
    BLOCK_INSERT, BLOCK_DELETE, BLOCK_SEQUENCE_UPDATE, TEXT_INSERT, TEXT_DELETE, TAG
}
