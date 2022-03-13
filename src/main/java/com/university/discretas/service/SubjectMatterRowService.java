package com.university.discretas.service;

import com.university.discretas.entity.SubjectMatter;
import com.university.discretas.entity.SubjectMatterRow;
import com.university.discretas.repository.SubjectMatterRowRepository;
import com.university.discretas.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubjectMatterRowService extends BaseService<SubjectMatterRow, Long, SubjectMatterRowRepository> {

    @Transactional
    public void deleteAllBySubjectMatter(SubjectMatter subjectMatter) {
        repository.deleteAllBySubjectMatter(subjectMatter);
    }
}
