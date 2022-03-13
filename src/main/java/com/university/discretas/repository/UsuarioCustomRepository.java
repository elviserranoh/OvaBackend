package com.university.discretas.repository;

import com.university.discretas.entity.RoleEnum;
import com.university.discretas.entity.Usuario;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class UsuarioCustomRepository implements IUsuarioCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Usuario> findUsuarioDinamicamente(String filterSearch, String valueSearch, Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
        Root<Usuario> root = cq.from(Usuario.class);
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasLength(filterSearch)) {
            predicates.add(cb.equal(root.get("rol"), RoleEnum.ROLE_USER.name()));
            predicates.add(cb.like(cb.lower(root.get(filterSearch)), "%" + valueSearch.toLowerCase() + "%"));
        }

        Predicate[] predArray = new Predicate[predicates.size()];
        predicates.toArray(predArray);

        cq.where(predArray);

        TypedQuery<Usuario> query = entityManager.createQuery(cq);

        int totalRows = query.getResultList().size();

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        Page<Usuario> result = new PageImpl<>(query.getResultList(), pageable, totalRows);

        return result;

    }
}
