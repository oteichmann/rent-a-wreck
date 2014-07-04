package com.prodyna.pac.rentawreck.backend.common.service.impl;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.prodyna.pac.rentawreck.backend.common.model.Role;
import com.prodyna.pac.rentawreck.backend.common.monitoring.Monitored;
import com.prodyna.pac.rentawreck.backend.common.service.RoleService;

/**
 * Implementation of the {@link RoleService} interface.
 * 
 * @author Oliver Teichmann
 * 
 */
@Stateless
@Monitored
public class RoleServiceBean extends AbstractEntityPersistenceServiceBean<Role> implements RoleService {

	@Inject
	private Logger log;

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#getEntityClass()
	 */
	@Override
	protected Class<Role> getEntityClass() {
		return Role.class;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#getLooger()
	 */
	@Override
	protected Logger getLooger() {
		return log;
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#getFindAllNamedQuery()
	 */
	@Override
	protected String getFindAllNamedQuery() {
		return Role.NQ_FIND_ALL;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#getFindAllCountNamedQuery()
	 */
	@Override
	protected String getFindAllCountNamedQuery() {
		return Role.NQ_FIND_ALL_COUNT;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.RoleService#findByName(java.lang.String)
	 */
	@Override
	public Role findByName(String name) {
		TypedQuery<Role> query = em.createQuery("SELECT x FROM Role x WHERE x.name = :name", Role.class);
		query.setParameter("name", name);
		
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
