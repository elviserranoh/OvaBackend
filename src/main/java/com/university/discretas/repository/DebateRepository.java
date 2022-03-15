package com.university.discretas.repository;

import com.university.discretas.entity.Debate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DebateRepository extends JpaRepository<Debate, Long> {

    List<Debate> findAllByTitleContainingIgnoreCase(@Param("title") String title);
}