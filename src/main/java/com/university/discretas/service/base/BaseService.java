package com.university.discretas.service.base;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public abstract class BaseService<T, ID, R extends JpaRepository<T, ID>> {

	@Autowired
	protected R repository;
	
	@Transactional
	public T save(T t) {
		return repository.save(t);
	}

	@Transactional(readOnly = true)
	public Optional<T> findById(ID id) {
		return repository.findById(id);
	}

	@Transactional(readOnly = true)
	public List<T> findAll() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public Page<T> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Transactional
	public T edit(T t) {
		return repository.save(t);
	}

	@Transactional
	public void delete(T t) {
		repository.delete(t);
	}

	@Transactional
	public void deleteById(ID id) {
		repository.deleteById(id);
	}
	
}