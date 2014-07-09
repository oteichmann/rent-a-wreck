package com.prodyna.pac.rentawreck.backend.common.service.impl;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;

import com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity;
import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;

public abstract class AbstractEntityPersistenceServiceBean<T extends AbstractEntity> implements AbstractEntityPersistenceService<T> {
	
	@Inject
	protected EntityManager em;
	
	@Override
	public T create(String uuid, T entity) {
		validateUuid(uuid, entity);
		return create(entity);
	}
	
	@Override
	public T create(T entity) {
		em.persist(entity);
		em.flush();
		return entity;
	}
	
	@Override
	public T read(String uuid) {
		getLooger().debug(String.format("Get %s by UUID: %s", getEntityClass().getName(), uuid));
		T entity = getEntityManager().find(getEntityClass(), uuid);
		return entity;
	}

	@Override
	public T update(String uuid, T entity) {
		validateUuid(uuid, entity);
		return update(entity);
	}
	
	@Override
	public T update(T entity) {
		entity = em.merge(entity);
		em.flush();
		return entity;
	}

	@Override
	public void delete(String uuid) {
		T entity = read(uuid);
		em.remove(entity);
		em.flush();
	}
	
	@Override
	public List<T> findAll() {
		getLooger().info(String.format("Get list of %s", getEntityClass().getName()));
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
	
	private void validateUuid(String uuid, T entity) {
		if(uuid != null && entity.getUuid() != null) {
			if(!uuid.equalsIgnoreCase(entity.getUuid())) {
				getLooger().error("UUID's must match!");	
				// TODO : Throw an exceptions.	
			}
		} else {
			getLooger().error("UUID must be set!");
			// TODO : Throw an exceptions.	
		}
	}
}
