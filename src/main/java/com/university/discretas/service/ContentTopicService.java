package com.university.discretas.service;

import com.university.discretas.entity.ContentTopic;
import com.university.discretas.entity.Topic;
import com.university.discretas.repository.ContentTopicRepository;
import com.university.discretas.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContentTopicService extends BaseService<ContentTopic, Long, ContentTopicRepository> {

    @Transactional
    public void deleteAllByTopic(Topic topic) {
        repository.deleteAllByTopic(topic);
    }
}
