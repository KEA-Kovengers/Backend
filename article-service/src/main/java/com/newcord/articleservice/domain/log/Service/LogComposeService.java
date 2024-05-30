package com.newcord.articleservice.domain.log.Service;

import com.newcord.articleservice.domain.log.dto.LogResponse;

import java.util.List;

public interface LogComposeService {

    LogResponse.EditorLogListResponseDTO getEditorsLogData(Long postID);

    LogResponse.BlockLogListDataResponseDTO getBlockCreator(Long creator_id);

    List<LogResponse.BlockCreatorDataResponseDTO> getBlockCreatorData(Long post_id);
}
