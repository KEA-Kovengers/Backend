package com.newcord.articleservice.domain.report.service;

import com.newcord.articleservice.domain.report.dto.ReportRequest.*;
import com.newcord.articleservice.domain.report.entity.Report;

public interface ReportCommandService {
    Report createReport(Long userID,CreateReportRequestDTO createReportRequestDTO);
}
