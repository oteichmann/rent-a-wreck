package com.prodyna.pac.rentawreck.backend.common.service.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity;
import com.prodyna.pac.rentawreck.backend.common.service.EntityPersistenceService;

public abstract class EntityPersistenceServiceBean<T extends AbstractEntity> implements EntityPersistenceService<T> {
	
	@Inject
	protected EntityManager em;
	
	@Override
	public T create(T entity) {
		if (getLooger().isLoggable(Level.FINE)) {
			getLooger().fine("Creating a new " + entity.getClass().getName());
		}
		getEntityManager().persist(entity);
		return entity;
	}
	
	@Override
	public T findById(String uuid) {
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
		T entity = findById(uuid);
		getEntityManager().remove(entity);
	}
	
	protected EntityManager getEntityManager() {
		return em;
	}

	protected abstract Class<T> getEntityClass();
	
	protected abstract Logger getLooger();
}
