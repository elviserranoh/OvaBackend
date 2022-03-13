package com.university.discretas.repository;

import com.university.discretas.entity.SubjectMatter;
import com.university.discretas.entity.SubjectMatterColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface SubjectMatterColumnRepository extends JpaRepository<SubjectMatterColumn, Long> {

    void deleteAllBySubjectMatter(@Param("subjectMatter") SubjectMatter subjectMatter);
}
