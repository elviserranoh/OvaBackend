package com.university.discretas.service;

import com.university.discretas.entity.SubjectMatter;
import com.university.discretas.entity.Topic;
import com.university.discretas.repository.SubjectMatterRepository;
import com.university.discretas.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SubjectMatterService extends BaseService<SubjectMatter, Long, SubjectMatterRepository> {

    public Page<SubjectMatter> findAllByTopic_TitleContainingIgnoreCase(String title, Pageable pageable) {
        return repository.findAllByTopic_TitleContainingIgnoreCase(title, pageable);
    }

    public List<SubjectMatter> findAllByTopic(@Param("topic") Topic topic) {
        return repository.findAllByTopic(topic);
    }
}
