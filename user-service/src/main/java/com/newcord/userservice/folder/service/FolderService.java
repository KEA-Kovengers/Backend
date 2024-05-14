package com.newcord.userservice.folder.service;

import com.newcord.userservice.folder.domain.FolderPost;
import com.newcord.userservice.folder.dto.FolderPostRequest.FolderPostRequestDTO;
import com.newcord.userservice.folder.dto.FolderPostResponse.FolderPostResponseDTO;
import com.newcord.userservice.folder.dto.FolderRequest.FolderUpdateRequestDTO;
import com.newcord.userservice.folder.dto.FolderRequest.FolderRequestDTO;
import com.newcord.userservice.folder.dto.FolderResponse.FolderUpdateResponseDTO;
import com.newcord.userservice.folder.dto.FolderResponse.FolderResponseDTO;

import java.util.List;

public interface FolderService {
    FolderResponseDTO addFolder(FolderRequestDTO folderAddDTO); // 폴더 생성
    FolderResponseDTO deleteFolder(FolderRequestDTO folderDeleteDTO); // 폴더 삭제
    List getFolderList(Long user_id); // 폴더 목록 조회
    FolderPostResponseDTO addFolderPost(FolderPostRequestDTO folderPostAddDTO); // 폴더에 게시글 추가
    FolderPostResponseDTO deleteFolderPost(FolderPostRequestDTO folderPostDeleteDTO); // 폴더에 게시글 추가
    List getFolderPostList(Long folder_id); // 폴더의 게시글 목록 조회
    FolderUpdateResponseDTO updateFolder(FolderUpdateRequestDTO folderUpdateDTO); // 폴더 업데이트
}

