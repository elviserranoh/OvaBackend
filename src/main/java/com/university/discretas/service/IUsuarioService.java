package com.university.discretas.service;

import com.university.discretas.dto.CreateUserRequest;
import com.university.discretas.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUsuarioService {
    List<Usuario> findByAll();

    Page<Usuario> findByAll(Pageable pageable);
    Page<Usuario> findAllByRolEquals(Pageable pageable);
    Page<Usuario> findByAllByTextSearch(Pageable pageable, String filterSearch, String valueSearch);


    Usuario findById(Long id);

    void deleteById(Long id);

    Usuario findByIdentityDocument(String identity);

    Usuario save(CreateUserRequest usuarioDTO);

    Usuario update(Usuario usuario);
}
