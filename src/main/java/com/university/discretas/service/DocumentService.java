package com.university.discretas.service;

import com.university.discretas.entity.Document;
import com.university.discretas.repository.DocumentRepository;
import com.university.discretas.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DocumentService extends BaseService<Document, Long, DocumentRepository> {

    @Transactional(readOnly = true)
    public Page<Document> findAllByTitleContainingIgnoreCase(String title, Pageable pageable) {
        return repository.findAllByTitleContainingIgnoreCase(title, pageable);
    }
}