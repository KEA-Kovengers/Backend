package com.newcord.articleservice.domain.hashtags.service;

import com.newcord.articleservice.domain.hashtags.entity.Hashtags;

public interface HashtagsCommandService {
    Hashtags createHashtags(String tagName);
    Hashtags deleteHashtagsByID(Long id);
    Hashtags deleteHashtagsByTagName(String tagName);
}
