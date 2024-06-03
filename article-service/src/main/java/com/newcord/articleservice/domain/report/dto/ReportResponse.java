package com.newcord.articleservice.domain.report.dto;


import com.newcord.articleservice.domain.report.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReportResponse {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportResponseDTO{
        private Report report;
        private String title;
    }

}
