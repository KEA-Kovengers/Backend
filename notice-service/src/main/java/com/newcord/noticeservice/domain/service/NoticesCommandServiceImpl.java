package com.newcord.noticeservice.domain.service;

import com.newcord.noticeservice.domain.dto.NoticesRequest.NoticesRequestDTO;
import com.newcord.noticeservice.domain.dto.NoticesResponse.NoticesResponseDTO;
import com.newcord.noticeservice.domain.entity.Notices;
import com.newcord.noticeservice.domain.repository.NoticesRepository;
import com.newcord.noticeservice.global.common.exception.ApiException;
import com.newcord.noticeservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;

import static com.newcord.noticeservice.domain.entity.StatusType.NOT_READ;
import static com.newcord.noticeservice.domain.entity.StatusType.READ;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticesCommandServiceImpl implements NoticesCommandService {
    private final NoticesRepository noticesRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public NoticesResponseDTO addNotices(NoticesRequestDTO noticesRequestDTO) {
        Notices newNotice = Notices.builder()
                .user_id(noticesRequestDTO.getUser_id())
                .from_id(noticesRequestDTO.getFrom_id())
                .post_id(noticesRequestDTO.getPost_id())
                .comment_id(noticesRequestDTO.getComment_id())
                .type(noticesRequestDTO.getType())
                .status(NOT_READ)
                .created_at(LocalDateTime.now())
                .build();
        Notices savedNotice = noticesRepository.save(newNotice);
        return NoticesResponseDTO.builder()
                .id(savedNotice.getId())
                .user_id(noticesRequestDTO.getUser_id())
                .from_id(noticesRequestDTO.getFrom_id())
                .post_id(noticesRequestDTO.getPost_id())
                .comment_id(noticesRequestDTO.getComment_id())
                .type(savedNotice.getType())
                .status(savedNotice.getStatus())
                .created_at(savedNotice.getCreated_at())
                .build();
    }

    @Override
    public NoticesResponseDTO updateStatus(String noticeId){
        Notices notice = noticesRepository.findById(new ObjectId(noticeId)).orElseThrow(() -> new ApiException(ErrorStatus._NOTICE_NOT_FOUND));

        Query query = new Query(Criteria.where("_id").is(noticeId));
        Update update = Update.update("status", READ);
        mongoTemplate.updateFirst(query, update, Notices.class);

        return NoticesResponseDTO.builder()
                .id(notice.getId())
                .user_id(notice.getUser_id())
                .from_id(notice.getFrom_id())
                .post_id(notice.getPost_id())
                .comment_id(notice.getComment_id())
                .type(notice.getType())
                .status(READ)
                .created_at(notice.getCreated_at())
                .build();
    }

}
