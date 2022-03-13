package com.university.discretas.repository;

import com.university.discretas.entity.SubjectMatter;
import com.university.discretas.entity.SubjectMatterAnswerCorrect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface SubjectMatterAnswerCorrectRepository extends JpaRepository<SubjectMatterAnswerCorrect, Long> {

    void deleteAllBySubjectMatter(@Param("subjectMatter") SubjectMatter subjectMatter);
}
