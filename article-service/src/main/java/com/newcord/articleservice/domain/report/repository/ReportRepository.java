package com.newcord.articleservice.domain.report.repository;

import com.newcord.articleservice.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT c FROM Report c WHERE c.user_id =:userID")
    List<Report> findAllByUser_id(Long userID);
}
