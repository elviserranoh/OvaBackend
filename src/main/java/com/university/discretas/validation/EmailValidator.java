package com.university.discretas.validation;

import com.university.discretas.entity.Usuario;
import com.university.discretas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {

    private final UsuarioRepository usuarioRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        return usuario == null;
    }

}