package com.university.discretas.repository;

import com.university.discretas.entity.SubjectMatter;
import com.university.discretas.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectMatterRepository  extends JpaRepository<SubjectMatter, Long> {

    Page<SubjectMatter> findAllByTopic_TitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);
    List<SubjectMatter> findAllByTopic(@Param("topic") Topic topic);
}
