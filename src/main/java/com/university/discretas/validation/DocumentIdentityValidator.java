package com.university.discretas.validation;

import com.university.discretas.entity.Usuario;
import com.university.discretas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class DocumentIdentityValidator implements ConstraintValidator<DocumentIdentityConstraint, String> {
	
	private final UsuarioRepository usuarioRepository;

	@Override
	public boolean isValid(String documentIdentity, ConstraintValidatorContext context) {
		Usuario usuario = usuarioRepository.findByIdentityDocument(documentIdentity);
		return usuario == null;
	}

}
