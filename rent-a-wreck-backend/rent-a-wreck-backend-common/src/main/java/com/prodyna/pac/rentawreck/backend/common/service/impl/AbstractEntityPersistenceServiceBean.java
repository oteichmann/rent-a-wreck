package com.prodyna.pac.rentawreck.backend.common.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity;
import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;

public abstract class AbstractEntityPersistenceServiceBean<T extends AbstractEntity> implements AbstractEntityPersistenceService<T> {
	
	@Inject
	protected EntityManager em;
	
	@Override
	public T create(T entity) {
		if (getLooger().isLoggable(Level.FINE)) {
			getLooger().fine("Creating a new " + entity.getClass().getName());
		}
		em.persist(entity);
		em.flush();
		return entity;
	}
	
	@Override
	public T read(String uuid) {
		T entity = getEntityManager().find(getEntityClass(), uuid);
		return entity;
	}

	@Override
	public T update(T entity) {
		entity = getEntityManager().merge(entity);
		return entity;
	}

	@Override
	public void delete(String uuid) {
		T entity = read(uuid);
		getEntityManager().remove(entity);
	}
	
	@Override
	public List<T> findAll() {
		TypedQuery<T> query = em.createNamedQuery(getFindAllNamedQuery(), getEntityClass());
		List<T> results = query.getResultList();

		return Collections.unmodifiableList(results);
	}

	@Override
	public Integer findAllCount() {
		int count = ((Number)em.createNamedQuery(getFindAllCountNamedQuery()).getSingleResult()).intValue();
		return count;
	}
	
	protected EntityManager getEntityManager() {
		return em;
	}

	protected abstract Class<T> getEntityClass();
	
	protected abstract Logger getLooger();

	protected abstract String getFindAllNamedQuery();

	protected abstract String getFindAllCountNamedQuery();
}
