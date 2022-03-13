package com.university.discretas.repository;

import com.university.discretas.entity.SubjectMatter;
import com.university.discretas.entity.SubjectMatterRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface SubjectMatterRowRepository extends JpaRepository<SubjectMatterRow, Long> {

    void deleteAllBySubjectMatter(@Param("subjectMatter") SubjectMatter subjectMatter);
}