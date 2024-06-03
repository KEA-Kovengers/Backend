package com.newcord.articleservice.domain.report.service;

import com.newcord.articleservice.domain.report.dto.ReportResponse;
import com.newcord.articleservice.domain.report.entity.Report;
import com.newcord.articleservice.domain.report.type.ReportType;

import java.util.List;

public interface ReportQueryService {
    // 관리자가 쓰는 전체 신고 내역 조회
    List<Report> getReportList();

    List<Report> getUsersReportList(Long userID);

    Report getReportDetail(Long id);

    List<ReportResponse.ReportResponseDTO> getEachReportList(ReportType type);
}
