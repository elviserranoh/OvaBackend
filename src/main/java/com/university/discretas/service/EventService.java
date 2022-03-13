package com.university.discretas.service;

import com.university.discretas.entity.Event;
import com.university.discretas.repository.EventRepository;
import com.university.discretas.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService extends BaseService<Event, Long, EventRepository> {

    @Transactional(readOnly = true)
    public Page<Event> findAllByLocationContainingIgnoreCase(String location, Pageable pageable) {
        return repository.findAllByLocationContainingIgnoreCase(location, pageable);
    }
}