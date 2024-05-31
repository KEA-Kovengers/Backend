package com.newcord.noticeservice.domain.service;

import com.newcord.noticeservice.domain.dto.NoticesResponse.NoticesResponseDTO;

import java.util.List;

public interface NoticesQueryService {
    List<NoticesResponseDTO> getNoticeList(Long userId); // 알림 조회
}
