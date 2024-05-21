package com.newcord.userservice.folder.service;

import com.newcord.userservice.folder.domain.Folder;
import com.newcord.userservice.folder.domain.FolderPost;
import com.newcord.userservice.folder.dto.FolderPostRequest;
import com.newcord.userservice.folder.dto.FolderPostResponse;
import com.newcord.userservice.folder.dto.FolderRequest;
import com.newcord.userservice.folder.dto.FolderResponse;
import com.newcord.userservice.folder.repository.FolderPostRepository;
import com.newcord.userservice.folder.repository.FolderRepository;
import com.newcord.userservice.global.common.exception.ApiException;
import com.newcord.userservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderCommandServiceImpl implements FolderCommandService{
    private final FolderRepository folderRepository;
    private final FolderPostRepository folderPostRepository;

    @Override
    public FolderResponse.FolderResponseDTO addFolder(Long userID, FolderRequest.FolderRequestDTO folderAddDTO) {
        String folderName = folderAddDTO.getFolderName();

        Folder existingFolder = folderRepository.findByUser_idAndFolderName(userID, folderName);
        if (existingFolder != null) {
            throw new ApiException(ErrorStatus._FOLDER_ALREADY_EXISTS);
        }

        Folder newFolder = folderAddDTO.toEntity(userID, folderAddDTO);
        Folder savedFolder = folderRepository.save(newFolder);
        return FolderResponse.FolderResponseDTO.builder()
                .id(savedFolder.getId())
                .build();
    }

    @Override
    public FolderResponse.FolderResponseDTO deleteFolder(Long userID, FolderRequest.FolderRequestDTO folderDeleteDTO) {
        String folderName = folderDeleteDTO.getFolderName();
        Folder folderToDelete = folderRepository.findByUser_idAndFolderName(userID, folderName);
        if (folderToDelete != null) {
            List<FolderPost> folderPostsToDelete = folderPostRepository.findByFolder_id(folderToDelete.getId());
            folderPostRepository.deleteAll(folderPostsToDelete);
            folderRepository.delete(folderToDelete);
            return FolderResponse.FolderResponseDTO.builder()
                    .id(folderToDelete.getId())
                    .build();
        } else {
            throw new ApiException(ErrorStatus._FOLDER_NOT_FOUND);
        }
    }

    @Override
    public FolderPostResponse.FolderPostResponseDTO addFolderPost(FolderPostRequest.FolderPostRequestDTO folderPostAddDTO) {
        Long folderId = folderPostAddDTO.getFolder_id();
        Long postId = folderPostAddDTO.getPost_id();

        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new ApiException(ErrorStatus._FOLDER_NOT_FOUND));
        FolderPost existingFolderPost = folderPostRepository.findByFolder_idAndPost_id(folderId, postId);
        if (existingFolderPost != null) {
            throw new ApiException(ErrorStatus._FOLDERPOST_ALREADY_EXISTS);
        }

        FolderPost newFolderPost = folderPostAddDTO.toEntity(folderPostAddDTO);
        FolderPost savedFolderPost = folderPostRepository.save(newFolderPost);
        return FolderPostResponse.FolderPostResponseDTO.builder()
                .folder_id(savedFolderPost.getFolder_id())
                .post_id(savedFolderPost.getPost_id())
                .build();
    }

    @Override
    public FolderPostResponse.FolderPostResponseDTO deleteFolderPost(FolderPostRequest.FolderPostRequestDTO folderPostDeleteDTO) {
        Long folderId = folderPostDeleteDTO.getFolder_id();
        Long postId = folderPostDeleteDTO.getPost_id();

        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new ApiException(ErrorStatus._FOLDER_NOT_FOUND));
        FolderPost folderPostToDelete = folderPostRepository.findByFolder_idAndPost_id(folderId, postId);
        if (folderPostToDelete != null) {
            folderPostRepository.delete(folderPostToDelete);
            return FolderPostResponse.FolderPostResponseDTO.builder()
                    .folder_id(folderPostToDelete.getFolder_id())
                    .post_id(folderPostToDelete.getPost_id())
                    .build();
        } else{
            throw new ApiException(ErrorStatus._FOLDERPOST_NOT_FOUND);
        }
    }

    @Override
    public FolderResponse.FolderUpdateResponseDTO updateFolder(FolderRequest.FolderUpdateRequestDTO folderUpdateDTO) {
        Long folderId = folderUpdateDTO.getFolder_id();
        String newFolderName = folderUpdateDTO.getFolderName();
        List<Long> newPostIds = folderUpdateDTO.getPostIds();

        Folder folderToUpdate = folderRepository.findById(folderId).orElseThrow(() -> new ApiException(ErrorStatus._FOLDER_NOT_FOUND));
        folderRepository.updateFolderName(folderId, newFolderName);

        folderPostRepository.deleteByFolderId(folderId);

        for (Long postId : newPostIds) {
            FolderPost newFolderPost = FolderPost.builder()
                    .folder_id(folderId)
                    .post_id(postId)
                    .build();
            folderPostRepository.save(newFolderPost);
        }

        return FolderResponse.FolderUpdateResponseDTO.builder()
                .folder_id(folderId)
                .folderName(newFolderName)
                .postIds(newPostIds)
                .build();
    }
}
