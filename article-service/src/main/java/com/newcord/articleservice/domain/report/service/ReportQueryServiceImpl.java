package com.newcord.articleservice.domain.report.service;

import com.newcord.articleservice.domain.report.entity.Report;
import com.newcord.articleservice.domain.report.repository.ReportRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportQueryServiceImpl implements ReportQueryService {

    private final ReportRepository reportRepository;

    // 관리자가 쓰는 전체 신고 내역 조회
    @Override
    public List<Report> getReportList(){
        return reportRepository.findAll();
    }

    @Override
    public List<Report> getUsersReportList(Long userID){
        return reportRepository.findAllByUser_id(userID);
    }

    @Override
    public Report getReportDetail(Long id){
        Report report = reportRepository.findById(id).orElse(null);
        if(report == null)
            throw new ApiException(ErrorStatus._REPORT_NOT_FOUND);;
        return report;
    }

    @Override
    public List<Report> getEachReportList(String type){
        return reportRepository.findAllByType(type);
    }
}
