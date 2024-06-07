package com.newcord.articleservice.domain.report.service;

import com.newcord.articleservice.domain.comments.entity.Comments;
import com.newcord.articleservice.domain.comments.repository.CommentsRepository;
import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.domain.posts.repository.PostsRepository;
import com.newcord.articleservice.domain.report.dto.ReportResponse.*;
import com.newcord.articleservice.domain.report.entity.Report;
import com.newcord.articleservice.domain.report.repository.ReportRepository;
import com.newcord.articleservice.domain.report.type.ReportType;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportQueryServiceImpl implements ReportQueryService {

    private final ReportRepository reportRepository;
    private final CommentsRepository commentsRepository;
    private final PostsRepository postsRepository;

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
    public List<ReportResponseDTO> getEachReportList(ReportType type){
        List<Report> report=reportRepository.findAllByType(type);
        List<ReportResponseDTO> result=new ArrayList<>();
        if(type==ReportType.COMMENT){
            for(Report r: report){
                Optional<Comments> comments= commentsRepository.findById(r.getContentID());
                ReportResponseDTO reportResponse=ReportResponseDTO.builder()
                        .report(r)
                        .title(comments.get().getBody())
                        .build();
            result.add(reportResponse);
            //return result;
            }
        }
        if(type==ReportType.POST){
            for(Report r: report) {
                Optional<Posts> posts=postsRepository.findById(r.getContentID());
                ReportResponseDTO reportResponseDTO=ReportResponseDTO.builder()
                        .report(r)
                        .title(posts.get().getTitle())
                        .build();

                result.add(reportResponseDTO);

            }
        }
        return result;
    }
}
