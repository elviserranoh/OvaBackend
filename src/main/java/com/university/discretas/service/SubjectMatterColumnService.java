package com.university.discretas.service;

import com.university.discretas.entity.SubjectMatter;
import com.university.discretas.entity.SubjectMatterColumn;
import com.university.discretas.repository.SubjectMatterColumnRepository;
import com.university.discretas.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubjectMatterColumnService extends BaseService<SubjectMatterColumn, Long, SubjectMatterColumnRepository> {

    @Transactional
    public void deleteAllBySubjectMatter(SubjectMatter subjectMatter) {
        repository.deleteAllBySubjectMatter(subjectMatter);
    }
}
