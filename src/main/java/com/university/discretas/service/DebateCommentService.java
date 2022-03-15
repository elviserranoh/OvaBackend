package com.university.discretas.service;

import com.university.discretas.entity.Debate;
import com.university.discretas.entity.DebateComment;
import com.university.discretas.repository.DebateCommentRepository;
import com.university.discretas.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DebateCommentService extends BaseService<DebateComment, Long, DebateCommentRepository> {

    @Transactional(readOnly = true)
    public List<DebateComment> findAllByDebate(Debate debate) {
        return repository.findAllByDebate(debate);
    }
}