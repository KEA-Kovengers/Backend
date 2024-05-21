package com.newcord.articleservice.domain.report.service;
import com.newcord.articleservice.domain.report.dto.ReportRequest.*;

import com.newcord.articleservice.domain.report.entity.Report;
import com.newcord.articleservice.domain.report.repository.ReportRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
@Transactional
public class ReportCommandServiceImpl implements ReportCommandService {
    private final ReportRepository reportRepository;

    @Override
    public Report createReport(CreateReportRequestDTO createReportRequestDTO){
        reportRepository.findById(createReportRequestDTO.getId()).orElseThrow(()-> new ApiException(ErrorStatus._NOTICE_ALREADY_EXISTS));

        Report report = Report.builder()
                        .user_id(createReportRequestDTO.getUserID())
                .body(createReportRequestDTO.getBody())
                .build();
        reportRepository.save(report);

        return report;
    }
}
