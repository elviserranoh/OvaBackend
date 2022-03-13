package com.university.discretas.repository;

import com.university.discretas.entity.Event;
import com.university.discretas.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAllByLocationContainingIgnoreCase(@Param("location") String location, Pageable pageable);
}
