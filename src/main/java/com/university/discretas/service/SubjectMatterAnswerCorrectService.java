package com.university.discretas.service;

import com.university.discretas.entity.SubjectMatter;
import com.university.discretas.entity.SubjectMatterAnswerCorrect;
import com.university.discretas.repository.SubjectMatterAnswerCorrectRepository;
import com.university.discretas.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubjectMatterAnswerCorrectService extends BaseService<SubjectMatterAnswerCorrect, Long, SubjectMatterAnswerCorrectRepository> {

    @Transactional
    public void deleteAllBySubjectMatter(SubjectMatter subjectMatter) {
        repository.deleteAllBySubjectMatter(subjectMatter);
    }
}
