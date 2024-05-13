package com.newcord.userservice.folder.repository;

import com.newcord.userservice.folder.domain.Folder;
import com.newcord.userservice.folder.domain.FolderPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderPostRepository extends JpaRepository<FolderPost, Long> {

    @Query("SELECT fp FROM FolderPost fp WHERE fp.folder_id = :folder_id")
    List<FolderPost> findByFolder_id(Long folder_id);

    @Query("SELECT fp FROM FolderPost fp WHERE fp.folder_id = :folder_id AND fp.post_id = :post_id")
    FolderPost findByFolder_idAndPost_id(Long folder_id, Long post_id);
}
