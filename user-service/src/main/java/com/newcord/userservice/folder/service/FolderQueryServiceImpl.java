package com.newcord.userservice.folder.service;

import com.newcord.userservice.folder.domain.Folder;
import com.newcord.userservice.folder.domain.FolderPost;
import com.newcord.userservice.folder.dto.FolderPostResponse;
import com.newcord.userservice.folder.dto.FolderResponse;
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
public class FolderQueryServiceImpl implements FolderQueryService {

    private final FolderRepository folderRepository;
    private final FolderPostRepository folderPostRepository;

    @Override
    public List<FolderResponse.FolderListResponseDTO> getFolderList(Long user_id) {
        List<Folder> folders = folderRepository.findByUser_id(user_id);
        return folders.stream()
                .map(folder -> FolderResponse.FolderListResponseDTO.builder()
                        .id(folder.getId())
                        .folderName(folder.getFolderName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<FolderPostResponse.FolderPostListResponseDTO> getFolderPostList(Long folder_id) {
        Folder folder = folderRepository.findById(folder_id).orElseThrow(() -> new ApiException(ErrorStatus._FOLDER_NOT_FOUND));
        List<FolderPost> posts = folderPostRepository.findByFolder_id(folder_id);
        return posts.stream()
                .map(folderPost -> FolderPostResponse.FolderPostListResponseDTO.builder()
                        .post_id(folderPost.getPost_id())
                        .build())
                .collect(Collectors.toList());
    }

}
