package com.newcord.userservice.domain.folder.repository;

import com.newcord.userservice.domain.folder.domain.FolderPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface FolderPostRepository extends JpaRepository<FolderPost, Long> {

    @Query("SELECT fp FROM FolderPost fp WHERE fp.folder_id = :folder_id")
    List<FolderPost> findByFolder_id(Long folder_id);

    @Query("SELECT fp FROM FolderPost fp WHERE fp.folder_id = :folder_id AND fp.post_id = :post_id")
    FolderPost findByFolder_idAndPost_id(Long folder_id, Long post_id);

    @Modifying
    @Query("DELETE FROM FolderPost fp WHERE fp.folder_id = :folder_id")
    void deleteByFolderId(Long folder_id);
}
