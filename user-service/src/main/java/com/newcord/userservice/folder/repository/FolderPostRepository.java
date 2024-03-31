package com.newcord.userservice.folder.repository;

import com.newcord.userservice.folder.domain.FolderPost;
import com.newcord.userservice.folder.domain.FolderPostId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderPostRepository extends JpaRepository<FolderPost, FolderPostId> {

}
