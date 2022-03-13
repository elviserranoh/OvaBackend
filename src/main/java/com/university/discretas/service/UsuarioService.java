package com.university.discretas.service;

import com.university.discretas.dto.CreateUserRequest;
import com.university.discretas.entity.RoleEnum;
import com.university.discretas.entity.Usuario;
import com.university.discretas.repository.IUsuarioCustomRepository;
import com.university.discretas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService, UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

	private final UsuarioRepository usuarioDao;
	private final PasswordEncoder passwordEncoder;
	private final IUsuarioCustomRepository usuarioCustomRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String identityDocument) throws UsernameNotFoundException {

		Usuario usuario = usuarioDao.findByIdentityDocument(identityDocument);

		if (usuario == null) {
			logger.error(String.format("Error en el login, la cedula o pasaporte: %s no existe", identityDocument));
			throw new UsernameNotFoundException(
					String.format("Error en el login, el pasaporte o cedula: %s no existe", identityDocument));
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(usuario.getRol()));

		return new User(usuario.getIdentityDocument(), usuario.getPassword(), true, true, true, true, authorities);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByIdentityDocument(String identityDocument) {
		return usuarioDao.findByIdentityDocument(identityDocument);
	}

	@Override
	@Transactional
	public Usuario save(CreateUserRequest userRequest) {

		String passwordCrypt = "";

		if(userRequest.getPassword().isEmpty()) {
			passwordCrypt = passwordEncoder.encode(userRequest.getIdentityDocument());
		} else {
			passwordCrypt = passwordEncoder.encode(userRequest.getPassword());
		}

		Usuario usuario = Usuario.builder()
				.firstName(userRequest.getFirstName())
				.lastName(userRequest.getLastName())
				.identityDocument(userRequest.getIdentityDocument())
				.password(passwordCrypt)
				.email(userRequest.getEmail())
				.phone(userRequest.getPhone())
				.rol(RoleEnum.ROLE_USER.name())
				.build();

		return usuarioDao.save(usuario);
	}
	

	@Override
	public Usuario update(Usuario usuario) {
		return usuarioDao.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findByAll() {
		return usuarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findByAll(Pageable pageable) {
		return usuarioDao.findAll(pageable);
	}

	@Override
	public Page<Usuario> findAllByRolEquals(Pageable pageable) {
		return usuarioDao.findAllByRolEquals(RoleEnum.ROLE_USER.name(), pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findByAllByTextSearch(Pageable pageable, String filterSearch, String valueSearch) {
		return usuarioCustomRepository.findUsuarioDinamicamente(filterSearch, valueSearch, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		usuarioDao.deleteById(id);
	}

}
