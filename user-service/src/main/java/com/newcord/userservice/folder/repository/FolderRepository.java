package com.newcord.userservice.folder.repository;

import com.newcord.userservice.folder.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {

    @Query("SELECT f FROM Folder f WHERE f.user_id = :user_id AND f.folderName = :folderName")
    Folder findByUser_idAndFolderName(Long user_id, String folderName);

    @Query("SELECT f FROM Folder f WHERE f.user_id = :user_id")
    List<Folder> findByUser_id(Long user_id);
}
