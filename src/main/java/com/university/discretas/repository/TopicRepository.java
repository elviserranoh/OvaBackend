package com.university.discretas.repository;

import com.university.discretas.entity.Ova;
import com.university.discretas.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findAllByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);
    List<Topic> findAllByOva(@Param("ova") Ova ova);
}
