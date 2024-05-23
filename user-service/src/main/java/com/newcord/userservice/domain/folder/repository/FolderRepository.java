package com.newcord.userservice.domain.folder.repository;

import com.newcord.userservice.domain.folder.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {

    @Query("SELECT f FROM Folder f WHERE f.user_id = :user_id AND f.folderName = :folderName")
    Folder findByUser_idAndFolderName(Long user_id, String folderName);

    @Query("SELECT f FROM Folder f WHERE f.user_id = :user_id")
    List<Folder> findByUser_id(Long user_id);

    @Modifying
    @Query("UPDATE Folder f SET f.folderName = :folderName WHERE f.id = :id")
    void updateFolderName(Long id, String folderName);
}
