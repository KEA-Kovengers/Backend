package com.newcord.articleservice.domain.comments.service;

import com.newcord.articleservice.domain.comments.dto.CommentsResponse.*;
import com.newcord.articleservice.domain.comments.entity.Comments;
import com.newcord.articleservice.domain.comments.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsQueryServiceImpl implements CommentsQueryService{

    private final CommentsRepository commentsRepository;

    @Override
    public List<CommentsResponseDTO> getCommentsList(Long postID){

        List<CommentsResponseDTO> result = new ArrayList<>();
        List<Comments> comments=commentsRepository.findByPost_id(postID);
        for(Comments c: comments){
            CommentsResponseDTO dto=CommentsResponseDTO.builder().
                    id(c.getId())
                    .commentID(c.getComment_id())
                    .userID(c.getUser_id())
                    .body(c.getBody())
            .build();
            result.add(dto);
        }
        return result;
    }
}
