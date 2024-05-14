package com.newcord.userservice.folder.dto;
import com.newcord.userservice.folder.domain.Folder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class FolderRequest {

    @Builder
    @Getter
    public static class FolderRequestDTO {
        private Long user_id;
        private String folderName;

        public Folder toEntity(FolderRequestDTO dto){
            return Folder.builder()
                    .user_id(dto.getUser_id())
                    .folderName(dto.getFolderName())
                    .build();
        }

    }

    @Builder
    @Getter
    @Setter
    public static class FolderUpdateRequestDTO {
        private Long folder_id;
        private String folderName;
        private List<Long> postIds;

    }


}
