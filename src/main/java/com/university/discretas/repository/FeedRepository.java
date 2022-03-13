package com.university.discretas.repository;

import com.university.discretas.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    Page<Feed> findAllByDescriptionContainingIgnoreCase(@Param("description") String description, Pageable pageable);
}
