package com.newcord.articleservice.domain.report.controller;

import com.newcord.articleservice.domain.report.dto.ReportRequest.*;
import com.newcord.articleservice.domain.report.entity.Report;
import com.newcord.articleservice.domain.report.service.ReportCommandService;
import com.newcord.articleservice.domain.report.service.ReportQueryService;
import com.newcord.articleservice.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Report", description = "신고 API")
@RequestMapping("/articles/report")
public class ReportController {
    private final ReportCommandService reportCommandService;
    private final ReportQueryService reportQueryService;

    @PostMapping("/comment")
    @Operation(summary = "댓글 신고 하기",description = "댓글 신고 api")
    public ApiResponse<Report> createCommentNotice(@RequestBody CreateReportRequestDTO createReportRequestDTO){
        return ApiResponse.onSuccess(reportCommandService.createReport(createReportRequestDTO));
    }

    @GetMapping("/all")
    @Operation(summary = "모든 신고 조회하기", description = "관리지가 사용하는 api입니다")
    public ApiResponse<List<Report>> getReportsList(){
        return ApiResponse.onSuccess(reportQueryService.getReportList());
    }

    @GetMapping("/{userid}")
    @Operation(summary = "내가 신고한 내역보기", description = "내가 신고한 내역 보는 api")
    public ApiResponse<List<Report>> getUserReportList(@PathVariable Long userid){
        return ApiResponse.onSuccess(reportQueryService.getUsersReportList(userid));
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "신고 상세보기",description = "해당 신고 클릭 시 상세보기")
    public ApiResponse<Report> getReportDetail(@PathVariable Long id){
        return ApiResponse.onSuccess(reportQueryService.getReportDetail(id));
    }
}
