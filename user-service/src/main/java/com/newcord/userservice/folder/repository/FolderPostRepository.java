package com.newcord.userservice.folder.repository;

import com.newcord.userservice.folder.domain.FolderPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderPostRepository extends JpaRepository<FolderPost, Long> {

}
