package com.prodyna.pac.rentawreck.backend.rentable.service.impl;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.prodyna.pac.rentawreck.backend.common.monitoring.Monitored;
import com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean;
import com.prodyna.pac.rentawreck.backend.rentable.model.Pilot;
import com.prodyna.pac.rentawreck.backend.rentable.service.PilotService;

/**
 * Implementation of the {@link PilotService} interface.
 *
 * @author Oliver Teichmann
 *
 */
@Stateless
@Monitored
public class PilotServiceBean extends AbstractEntityPersistenceServiceBean<Pilot> implements PilotService {

	@Inject
	private Logger log;
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#getEntityClass()
	 */
	@Override
	protected Class<Pilot> getEntityClass() {
		return Pilot.class;
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
		return Pilot.NQ_FIND_ALL;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#getFindAllCountNamedQuery()
	 */
	@Override
	protected String getFindAllCountNamedQuery() {
		return Pilot.NQ_FIND_ALL_COUNT;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.rentable.service.PilotService#readPilotForUser(java.lang.String)
	 */
	@Override
	public Pilot readPilotForUser(String userUuid) {
		TypedQuery<Pilot> query = em.createQuery("SELECT p FROM Pilot p JOIN p.user u WHERE u.uuid = :userUuid", Pilot.class);
		query.setParameter("userUuid", userUuid);
		try {
			Pilot pilot = query.getSingleResult();
			return pilot;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	

}
