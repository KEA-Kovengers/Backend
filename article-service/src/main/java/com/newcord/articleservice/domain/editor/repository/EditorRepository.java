package com.newcord.articleservice.domain.editor.repository;

import com.newcord.articleservice.domain.editor.entity.Editor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorRepository extends JpaRepository<Editor, Long> {
    Page<Editor> findByUserID(Long userID, Pageable pageable);
    Optional<Editor> findByPostIdAndUserID(Long postId, Long userID);
    List<Editor> findByPostId(Long postId);
    List<Editor> findAllByPostId(Long postId);

}
