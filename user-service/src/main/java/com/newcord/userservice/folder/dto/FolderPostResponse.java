package com.newcord.userservice.folder.dto;

import lombok.Builder;
import lombok.Getter;

public class FolderPostResponse {

    @Builder
    @Getter
    public static class FolderPostResponseDTO {
        private Long folder_id;
        private Long post_id;
    }

    @Builder
    @Getter
    public static class FolderPostListResponseDTO {
        private Long post_id;
    }

}
