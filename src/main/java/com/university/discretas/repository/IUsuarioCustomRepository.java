package com.university.discretas.repository;

import com.university.discretas.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuarioCustomRepository {
    Page<Usuario> findUsuarioDinamicamente(String filterSearch, String valueSearch, Pageable pageable);
}
