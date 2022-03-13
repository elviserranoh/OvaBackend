package com.university.discretas.service;

import com.university.discretas.entity.Feed;
import com.university.discretas.repository.FeedRepository;
import com.university.discretas.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedService extends BaseService<Feed, Long, FeedRepository> {

    @Transactional(readOnly = true)
    public Page<Feed> findAllByDescriptionContainingIgnoreCase(String name, Pageable pageable) {
        return repository.findAllByDescriptionContainingIgnoreCase(name, pageable);
    }
}