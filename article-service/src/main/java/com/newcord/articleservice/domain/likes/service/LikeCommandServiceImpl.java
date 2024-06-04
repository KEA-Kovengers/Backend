package com.newcord.articleservice.domain.likes.service;

import com.newcord.articleservice.domain.editor.repository.EditorRepository;
import com.newcord.articleservice.domain.likes.dto.LikeRequest.*;
import com.newcord.articleservice.domain.likes.entity.Likes;
import com.newcord.articleservice.domain.likes.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LikeCommandServiceImpl implements LikeCommandService{

    private final LikeRepository likeRepository;
    private final EditorRepository editorRepository;
    private final WebClient webClient = WebClient.builder().build();

    //좋아요
    @Override
    public Likes createLike(Long userID,CreateLikeRequestDTO createLikeRequestDTO){
        Likes likes=Likes.builder()
                .user_id(userID)
                .post_id(createLikeRequestDTO.getPost_id())
                .build();

        // post_id에 해당하는 모든 user_id 가져오기
        List<Long> userIds = editorRepository.findUserIdsByPostId(createLikeRequestDTO.getPost_id());
        // 알림 API 요청
        for (Long editorUserId : userIds) {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("user_id", editorUserId);
            requestBody.put("from_id", userID);
            requestBody.put("post_id", createLikeRequestDTO.getPost_id());
            requestBody.put("type", "LIKE");

            webClient.post()
                    .uri("http://newcord.kro.kr/notices/addNotice")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }

        likeRepository.save(likes);
        return likes;
    }

    //좋아요 취소
    @Override
    public String deleteLike(Long id){
        likeRepository.deleteById(id);
        return "좋아요가 취소되었습니다";
    }
}
