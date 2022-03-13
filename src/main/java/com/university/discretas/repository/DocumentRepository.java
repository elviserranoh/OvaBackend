package com.university.discretas.repository;

import com.university.discretas.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    Page<Document> findAllByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);
}