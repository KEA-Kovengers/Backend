package com.newcord.noticeservice.domain.service;

import com.newcord.noticeservice.domain.entity.Notices;
import com.newcord.noticeservice.domain.repository.NoticesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticesService {

    private final NoticesRepository noticesRepository;

    public Notices saveNotices(Notices notices) {
        return noticesRepository.save(notices);
    }
}