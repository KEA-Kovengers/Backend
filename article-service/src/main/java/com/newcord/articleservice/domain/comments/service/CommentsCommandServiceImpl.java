package com.newcord.articleservice.domain.comments.service;

import com.newcord.articleservice.domain.comments.dto.CommentsRequest.*;
import com.newcord.articleservice.domain.comments.entity.Comments;
import com.newcord.articleservice.domain.comments.repository.CommentsRepository;
import com.newcord.articleservice.domain.editor.repository.EditorRepository;
import com.newcord.articleservice.domain.posts.repository.PostsRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentsCommandServiceImpl implements CommentsCommandService{

    private final CommentsRepository commentsRepository;
    private final EditorRepository editorRepository;
    private final WebClient webClient = WebClient.builder().build();

    // 댓글 작성, 대댓글 작성
    // 댓글은 commentID가 id
    // 대댓글은 commentID에 부모 댓글의 id
    @Override
    public Comments createComment(Long userID,CommentsCreateRequestDTO commentsCreateRequestDTO){
        Comments comments=Comments.builder().
                comment_id(commentsCreateRequestDTO.getCommentID())
                .postId(commentsCreateRequestDTO.getPostID())
                .user_id(userID)
                .isDeleted(false)
                .body((commentsCreateRequestDTO.getBody()))
        .build();
        log.info(String.valueOf(comments.getComment_id()));
        commentsRepository.save(comments);


        // post_id에 해당하는 모든 user_id 가져오기
        List<Long> userIds = editorRepository.findUserIdsByPostId(commentsCreateRequestDTO.getPostID());
        // 알림 API 요청
        for (Long editorUserId : userIds) {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("user_id", editorUserId);
            requestBody.put("from_id", userID);
            requestBody.put("post_id", commentsCreateRequestDTO.getPostID());
            requestBody.put("comment_id", commentsCreateRequestDTO.getCommentID());

            // Comments의 id와 comment_id가 같으면 type COMMENT, 다르면 type RECOMMENT로 설정
            if (comments.getId().equals(comments.getComment_id())) {
                requestBody.put("type", "COMMENT");
            } else {
                requestBody.put("type", "RECOMMENT");
            }

            webClient.post()
                    .uri("http://newcord.kro.kr/notices/addNotice")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
        // comment_id를 id로 가진 Comments 객체의 user_id에도 알림 보내기
        commentsRepository.findByCommentId(comments.getComment_id()).ifPresent(parentComment -> {
            Long parentUserId = parentComment.getUser_id();
            if (!userIds.contains(parentUserId)) {
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("user_id", parentUserId);
                requestBody.put("from_id", userID);
                requestBody.put("post_id", comments.getId());
                requestBody.put("comment_id", parentComment.getId());
                requestBody.put("type", "RECOMMENT");

                webClient.post()
                        .uri("http://newcord.kro.kr/notices/addNotice")
                        .bodyValue(requestBody)
                        .retrieve()
                        .bodyToMono(Void.class)
                        .block();
            }
        });
        return comments;
    }

    @Override
    public Comments deleteComment(CommentsRequestDTO commentsRequestDTO){
        Comments comments= commentsRepository.findById(commentsRequestDTO.getId()).orElseThrow(()-> new ApiException(ErrorStatus._COMMENT_NOT_FOUND));
        comments.setDeleted(true);
        return comments;
    }
}
