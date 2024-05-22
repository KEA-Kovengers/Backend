package com.newcord.articleservice.domain.report.repository;

import com.newcord.articleservice.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT c FROM Report c WHERE c.userID =:userID")
    List<Report> findAllByUser_id(Long userID);

    @Query("SELECT c FROM Report c WHERE c.type =:type")
    List<Report> findAllByType(String type);

    List<Report> findAllByUserIDAndContentID(Long userID,Long contentID);


}
