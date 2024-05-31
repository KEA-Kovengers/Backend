package com.newcord.noticeservice.domain.service;

import com.newcord.noticeservice.domain.dto.NoticesRequest;
import com.newcord.noticeservice.domain.dto.NoticesResponse.NoticesResponseDTO;

public interface NoticesCommandService {
    NoticesResponseDTO addNotices(NoticesRequest.NoticesRequestDTO noticesRequestDTO); // 알림 DB 저장
    NoticesResponseDTO updateStatus(String noticeId); //
}
