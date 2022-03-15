package com.university.discretas.service;

import com.university.discretas.entity.Debate;
import com.university.discretas.repository.DebateRepository;
import com.university.discretas.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DebateService extends BaseService<Debate, Long, DebateRepository> {

    @Transactional(readOnly = true)
    public List<Debate> findAllByTitleContainingIgnoreCase(String title) {
        return repository.findAllByTitleContainingIgnoreCase(title);
    }
}