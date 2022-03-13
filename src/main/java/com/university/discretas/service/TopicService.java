package com.university.discretas.service;

import com.university.discretas.entity.Topic;
import com.university.discretas.repository.TopicRepository;
import com.university.discretas.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicService extends BaseService<Topic, Long, TopicRepository> {

    @Transactional(readOnly = true)
    public Page<Topic> findAllByTitleContainingIgnoreCase(String name, Pageable pageable) {
        return repository.findAllByTitleContainingIgnoreCase(name, pageable);
    }
}