package com.newcord.articleservice.domain.hashtags.service;

import com.newcord.articleservice.domain.hashtags.entity.Hashtags;
import java.util.List;
import java.util.Optional;

public interface HashtagsQueryService {
    Hashtags findByID(Long id);
    Optional<Hashtags> findByTagName(String tagName);
    Optional<Hashtags> findByTagNameOptional(String tagName);

    List<Hashtags> findIdByTagName(String tagName);

}
