package com.university.discretas.service;

import com.university.discretas.entity.Ova;
import com.university.discretas.entity.Topic;
import com.university.discretas.repository.TopicRepository;
import com.university.discretas.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TopicService extends BaseService<Topic, Long, TopicRepository> {

    @Transactional(readOnly = true)
    public Page<Topic> findAllByTitleContainingIgnoreCase(String name, Pageable pageable) {
        return repository.findAllByTitleContainingIgnoreCase(name, pageable);
    }

    @Transactional(readOnly = true)
    public List<Topic> findAllByOva(@Param("ova") Ova ova) {
        return repository.findAllByOva(ova);
    }
}