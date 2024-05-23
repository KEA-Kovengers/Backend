package com.newcord.userservice.domain.folder.service;

import java.util.List;

public interface FolderQueryService {
    List getFolderList(Long user_id); // 폴더 목록 조회
    List getFolderPostList(Long folder_id); // 폴더의 게시글 목록 조회
}
