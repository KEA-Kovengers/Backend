package com.newcord.noticeservice.domain.service;

import com.newcord.noticeservice.domain.dto.NoticesRequest;
import com.newcord.noticeservice.domain.dto.NoticesResponse.NoticesResponseDTO;
import com.newcord.noticeservice.domain.entity.Notices;
import com.newcord.noticeservice.domain.repository.NoticesRepository;
import com.newcord.noticeservice.global.common.exception.ApiException;
import com.newcord.noticeservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static com.newcord.noticeservice.domain.entity.NoticeType.LIKE;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticesQueryServiceImpl implements NoticesQueryService {
    private final NoticesRepository noticesRepository;

    @Override
    public List<NoticesResponseDTO> getNoticeList(Long userId) {

        //알림 발생
        RestTemplate restTemplate = new RestTemplate();
        NoticesRequest.NoticesRequestDTO noticesRequestDTO = NoticesRequest.NoticesRequestDTO.builder()
                .user_id(userId)
                .body("message")
                .type(LIKE)
                .build();
        String url = "http://localhost:8081/send/" + userId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NoticesRequest.NoticesRequestDTO> request = new HttpEntity<>(noticesRequestDTO, headers);
        restTemplate.exchange(url, HttpMethod.POST, request, Void.class);

        List<Notices> savedNotices = noticesRepository.findByUser_id(userId);
        if (savedNotices.isEmpty()) {
            throw new ApiException(ErrorStatus._NOTICE_NOT_FOUND);
        }

        return savedNotices.stream()
                .map(notices -> NoticesResponseDTO.builder()
                        .id(notices.getId())
                        .user_id(notices.getUser_id())
                        .body(notices.getBody())
                        .type(notices.getType())
                        .status(notices.getStatus())
                        .created_at(notices.getCreated_at())
                        .build())
                .collect(Collectors.toList());
    }
}