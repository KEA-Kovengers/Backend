package com.newcord.articleservice.domain.editor.repository;

import com.newcord.articleservice.domain.editor.entity.Editor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorRepository extends JpaRepository<Editor, String> {

}
