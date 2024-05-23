package com.newcord.articleservice.domain.report.service;
import com.newcord.articleservice.domain.report.dto.ReportRequest.*;

import com.newcord.articleservice.domain.report.entity.Report;
import com.newcord.articleservice.domain.report.repository.ReportRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReportCommandServiceImpl implements ReportCommandService {
    private final ReportRepository reportRepository;

    @Override
    public Report createReport(Long userID,CreateReportRequestDTO createReportRequestDTO){
   List<Report> reportList= reportRepository.findAllByUserIDAndContentID(userID, createReportRequestDTO.getContendID());
        if(!reportList.isEmpty()){
            log.info(reportList.toString());
            throw new ApiException((ErrorStatus._REPORT_ALREADY_EXISTS));
        }
        Report report = Report.builder()
                .contentID(createReportRequestDTO.getContendID())
                        .userID(userID)
                .body(createReportRequestDTO.getBody())
                .type(createReportRequestDTO.getType())
                .build();
        reportRepository.save(report);
        return report;
    }
}
