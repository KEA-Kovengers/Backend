package com.newcord.userservice.folder.dto;

import com.newcord.userservice.folder.domain.FolderPost;
import lombok.Builder;
import lombok.Getter;

public class FolderPostRequest {

    @Builder
    @Getter
    public static class FolderPostRequestDTO {
        private Long folder_id;
        private Long post_id;

        public FolderPost toEntity(FolderPostRequestDTO dto){
            return FolderPost.builder()
                    .folder_id(dto.getFolder_id())
                    .post_id(dto.getPost_id())
                    .build();
        }

    }
}
