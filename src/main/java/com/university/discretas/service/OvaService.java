package com.university.discretas.service;

import com.university.discretas.entity.Ova;
import com.university.discretas.repository.OvaRepository;
import com.university.discretas.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OvaService extends BaseService<Ova, Long, OvaRepository> {

    @Transactional(readOnly = true)
    public Page<Ova> findAllByNameIsLike(String name, Pageable pageable) {
        return repository.findAllByNameContainingIgnoreCase(name, pageable);
    }
}
