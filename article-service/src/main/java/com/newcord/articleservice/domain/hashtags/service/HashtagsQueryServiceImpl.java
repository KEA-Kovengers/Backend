package com.newcord.articleservice.domain.hashtags.service;

import com.newcord.articleservice.domain.hashtags.entity.Hashtags;
import com.newcord.articleservice.domain.hashtags.repository.HashtagsRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashtagsQueryServiceImpl implements HashtagsQueryService{
    private final HashtagsRepository hashtagsRepository;

    @Override
    public Hashtags findByID(Long id) {
        return hashtagsRepository.findById(id).orElseThrow(() -> new ApiException(
            ErrorStatus._HASHTAGS_NOT_FOUND));
    }

    @Override
    public Hashtags findByTagName(String tagName) {
        return hashtagsRepository.findByTagName(tagName).orElseThrow(() -> new ApiException(
            ErrorStatus._HASHTAGS_NOT_FOUND));
    }

    //이거 왜 안됨?
    @Override
    public Optional<Hashtags> findByTagNameOptional(String tagName) {
        return hashtagsRepository.findByTagName(tagName);
    }

}
