package com.university.discretas.repository;

import com.university.discretas.entity.ContentTopic;
import com.university.discretas.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ContentTopicRepository extends JpaRepository<ContentTopic, Long> {

    void deleteAllByTopic(@Param("topic") Topic topic);
}
