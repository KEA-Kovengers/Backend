package com.newcord.userservice.folder.service;

import com.newcord.userservice.folder.domain.Folder;
import com.newcord.userservice.folder.domain.FolderPost;
import com.newcord.userservice.folder.dto.FolderPostRequest.FolderPostRequestDTO;
import com.newcord.userservice.folder.dto.FolderPostResponse.FolderPostListResponseDTO;
import com.newcord.userservice.folder.dto.FolderPostResponse.FolderPostResponseDTO;
import com.newcord.userservice.folder.dto.FolderRequest.FolderUpdateRequestDTO;
import com.newcord.userservice.folder.dto.FolderRequest.FolderRequestDTO;
import com.newcord.userservice.folder.dto.FolderResponse.FolderUpdateResponseDTO;
import com.newcord.userservice.folder.dto.FolderResponse.FolderListResponseDTO;
import com.newcord.userservice.folder.dto.FolderResponse.FolderResponseDTO;
import com.newcord.userservice.folder.repository.FolderPostRepository;
import com.newcord.userservice.folder.repository.FolderRepository;
import com.newcord.userservice.global.common.exception.ApiException;
import com.newcord.userservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;
    private final FolderPostRepository folderPostRepository;

    @Override
    public FolderResponseDTO addFolder(FolderRequestDTO folderAddDTO) {
        String folderName = folderAddDTO.getFolderName();
        Long userId = folderAddDTO.getUser_id();

        Folder existingFolder = folderRepository.findByUser_idAndFolderName(userId, folderName);
        if (existingFolder != null) {
            throw new ApiException(ErrorStatus._FOLDER_ALREADY_EXISTS);
        }

        Folder newFolder = folderAddDTO.toEntity(folderAddDTO);
        Folder savedFolder = folderRepository.save(newFolder);
        return FolderResponseDTO.builder()
                .id(savedFolder.getId())
                .build();
    }

    @Override
    public FolderResponseDTO deleteFolder(FolderRequestDTO folderDeleteDTO) {
        Long userId = folderDeleteDTO.getUser_id();
        String folderName = folderDeleteDTO.getFolderName();
        Folder folderToDelete = folderRepository.findByUser_idAndFolderName(userId, folderName);
        if (folderToDelete != null) {
            List<FolderPost> folderPostsToDelete = folderPostRepository.findByFolder_id(folderToDelete.getId());
            folderPostRepository.deleteAll(folderPostsToDelete);
            folderRepository.delete(folderToDelete);
            return FolderResponseDTO.builder()
                    .id(folderToDelete.getId())
                    .build();
        } else {
            throw new ApiException(ErrorStatus._FOLDER_NOT_FOUND);
        }
    }

    @Override
    public List<FolderListResponseDTO> getFolderList(Long user_id) {
        List<Folder> folders = folderRepository.findByUser_id(user_id);
        return folders.stream()
                .map(folder -> FolderListResponseDTO.builder()
                        .id(folder.getId())
                        .folderName(folder.getFolderName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public FolderPostResponseDTO addFolderPost(FolderPostRequestDTO folderPostAddDTO) {
        Long folderId = folderPostAddDTO.getFolder_id();
        Long postId = folderPostAddDTO.getPost_id();

        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new ApiException(ErrorStatus._FOLDER_NOT_FOUND));
        FolderPost existingFolderPost = folderPostRepository.findByFolder_idAndPost_id(folderId, postId);
        if (existingFolderPost != null) {
            throw new ApiException(ErrorStatus._FOLDERPOST_ALREADY_EXISTS);
        }

        FolderPost newFolderPost = folderPostAddDTO.toEntity(folderPostAddDTO);
        FolderPost savedFolderPost = folderPostRepository.save(newFolderPost);
        return FolderPostResponseDTO.builder()
                .folder_id(savedFolderPost.getFolder_id())
                .post_id(savedFolderPost.getPost_id())
                .build();
    }

    @Override
    public FolderPostResponseDTO deleteFolderPost(FolderPostRequestDTO folderPostDeleteDTO) {
        Long folderId = folderPostDeleteDTO.getFolder_id();
        Long postId = folderPostDeleteDTO.getPost_id();

        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new ApiException(ErrorStatus._FOLDER_NOT_FOUND));
        FolderPost folderPostToDelete = folderPostRepository.findByFolder_idAndPost_id(folderId, postId);
        if (folderPostToDelete != null) {
            folderPostRepository.delete(folderPostToDelete);
            return FolderPostResponseDTO.builder()
                    .folder_id(folderPostToDelete.getFolder_id())
                    .post_id(folderPostToDelete.getPost_id())
                    .build();
        } else{
            throw new ApiException(ErrorStatus._FOLDERPOST_NOT_FOUND);
        }
    }

    @Override
    public List<FolderPostListResponseDTO> getFolderPostList(Long folder_id) {
        Folder folder = folderRepository.findById(folder_id).orElseThrow(() -> new ApiException(ErrorStatus._FOLDER_NOT_FOUND));
        List<FolderPost> posts = folderPostRepository.findByFolder_id(folder_id);
        return posts.stream()
                .map(folderPost -> FolderPostListResponseDTO.builder()
                        .post_id(folderPost.getPost_id())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public FolderUpdateResponseDTO updateFolder(FolderUpdateRequestDTO folderUpdateDTO) {
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

        return FolderUpdateResponseDTO.builder()
                .folder_id(folderId)
                .folderName(newFolderName)
                .postIds(newPostIds)
                .build();
    }

}


