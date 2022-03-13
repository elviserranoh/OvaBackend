package com.university.discretas.repository;

import com.university.discretas.entity.Ova;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface OvaRepository extends JpaRepository<Ova, Long> {

    Page<Ova> findAllByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

}
