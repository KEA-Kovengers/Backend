package com.newcord.articleservice.domain.hashtags.service;

import com.newcord.articleservice.domain.hashtags.entity.Hashtags;
import com.newcord.articleservice.domain.hashtags.repository.HashtagsRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashtagsCommandServiceImpl implements HashtagsCommandService{
    private final HashtagsRepository hashtagsRepository;

    @Override
    public Hashtags createHashtags(String tagName) {
        Hashtags tags = hashtagsRepository.findByTagName(tagName).orElse(null);

        if(tags != null){
            throw new ApiException(ErrorStatus._HASHTAGS_ALREADY_EXISTS);
        }

        Hashtags hashtags = Hashtags.builder()
                .tagName(tagName)
                .build();
        return hashtagsRepository.save(hashtags);
    }

    @Override
    public Hashtags deleteHashtagsByID(Long id) {
        Hashtags tags = hashtagsRepository.findById(id).orElseThrow(() -> new ApiException(
            ErrorStatus._HASHTAGS_NOT_FOUND));

        hashtagsRepository.delete(tags);

        return tags;
    }

    @Override
    public Hashtags deleteHashtagsByTagName(String tagName) {
        Hashtags tags = hashtagsRepository.findByTagName(tagName).orElseThrow(() -> new ApiException(
            ErrorStatus._HASHTAGS_NOT_FOUND));

        hashtagsRepository.delete(tags);

        return tags;
    }
}
