package com.newcord.articleservice.domain.report.dto;

import com.newcord.articleservice.domain.report.type.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReportRequest {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReportRequestDTO{
        private Long contendID;
        private String body;
        private ReportType type;
    }
}
