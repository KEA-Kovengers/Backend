package com.newcord.userservice.folder.dto;

import com.newcord.userservice.folder.domain.Folder;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class FolderResponse {

    @Builder
    @Getter
    public static class FolderResponseDTO {
        private Long id;
    }

    @Builder
    @Getter
    public static class FolderListResponseDTO {
        private Long id;
        private String folderName;

    }

    @Builder
    @Getter
    public static class FolderUpdateResponseDTO {
        private Long folder_id;
        private String folderName;
        private List<Long> postIds;
    }


}
