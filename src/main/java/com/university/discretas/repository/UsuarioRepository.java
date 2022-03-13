package com.university.discretas.repository;

import com.university.discretas.entity.Ova;
import com.university.discretas.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByIdentityDocument(String documentIdentity);
    Usuario findByEmail(String email);


    Page<Usuario> findAllByRolEquals(@Param("rol") String rol, Pageable pageable);
}
