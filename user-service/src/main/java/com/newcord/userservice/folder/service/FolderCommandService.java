package com.newcord.userservice.folder.service;

import com.newcord.userservice.folder.dto.FolderPostRequest;
import com.newcord.userservice.folder.dto.FolderPostResponse;
import com.newcord.userservice.folder.dto.FolderRequest;
import com.newcord.userservice.folder.dto.FolderResponse;

public interface FolderCommandService {
    FolderResponse.FolderResponseDTO addFolder(Long userID, FolderRequest.FolderRequestDTO folderAddDTO); // 폴더 생성
    FolderResponse.FolderResponseDTO deleteFolder(Long userID, FolderRequest.FolderRequestDTO folderDeleteDTO); // 폴더 삭제
    FolderPostResponse.FolderPostResponseDTO addFolderPost(FolderPostRequest.FolderPostRequestDTO folderPostAddDTO); // 폴더에 게시글 추가
    FolderPostResponse.FolderPostResponseDTO deleteFolderPost(FolderPostRequest.FolderPostRequestDTO folderPostDeleteDTO); // 폴더에 게시글 추가
    FolderResponse.FolderUpdateResponseDTO updateFolder(FolderRequest.FolderUpdateRequestDTO folderUpdateDTO); // 폴더 업데이트
}
