package com.university.discretas.repository;

import com.university.discretas.entity.Debate;
import com.university.discretas.entity.DebateComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DebateCommentRepository extends JpaRepository<DebateComment, Long> {

    List<DebateComment> findAllByDebate(@Param("debate") Debate debate);
}