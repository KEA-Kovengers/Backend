package com.newcord.noticeservice.domain.service;

import com.newcord.noticeservice.domain.dto.NoticesResponse.NoticesResponseDTO;
import com.newcord.noticeservice.domain.entity.Notices;
import com.newcord.noticeservice.domain.repository.NoticesRepository;
import com.newcord.noticeservice.global.common.exception.ApiException;
import com.newcord.noticeservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticesQueryServiceImpl implements NoticesQueryService {
    private final NoticesRepository noticesRepository;

    @Override
    public List<NoticesResponseDTO> getNoticeList(Long userId) {
        List<Notices> savedNotices = noticesRepository.findByUser_id(userId);
        if (savedNotices.isEmpty()) {
            throw new ApiException(ErrorStatus._NOTICE_NOT_FOUND);
        }

        return savedNotices.stream()
                .map(notices -> NoticesResponseDTO.builder()
                        .id(notices.getId())
                        .user_id(notices.getUser_id())
                        .from_id(notices.getFrom_id())
                        .post_id(notices.getPost_id())
                        .comment_id(notices.getComment_id())
                        .type(notices.getType())
                        .status(notices.getStatus())
                        .created_at(notices.getCreated_at())
                        .build())
                .collect(Collectors.toList());
    }
}