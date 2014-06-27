package com.prodyna.pac.rentawreck.backend.rentable.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import com.prodyna.pac.rentawreck.backend.common.monitoring.Monitored;
import com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean;
import com.prodyna.pac.rentawreck.backend.rentable.model.Charter;
import com.prodyna.pac.rentawreck.backend.rentable.service.CharterService;

/**
 * CharterServiceBean
 *
 * @author Oliver Teichmann
 *
 */
@Stateless
@Monitored
public class CharterServiceBean extends AbstractEntityPersistenceServiceBean<Charter> implements CharterService {

	@Inject
	private Logger log;
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#getEntityClass()
	 */
	@Override
	protected Class<Charter> getEntityClass() {
		return Charter.class;
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
		return Charter.NQ_FIND_ALL;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#getFindAllCountNamedQuery()
	 */
	@Override
	protected String getFindAllCountNamedQuery() {
		return Charter.NQ_FIND_ALL_COUNT;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.rentable.service.CharterService#getAircraftCharters(java.lang.String)
	 */
	@Override
	public List<Charter> getAircraftCharters(String aircraftUuid) {
		TypedQuery<Charter> query = em.createQuery("SELECT x FROM Charter x JOIN x.aircraft a WHERE a.uuid = :aircraftUuid", getEntityClass());
		query.setParameter("aircraftUuid", aircraftUuid);
		List<Charter> results = query.getResultList();

		return Collections.unmodifiableList(results);
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.rentable.service.CharterService#getPiltoCharters(java.lang.String)
	 */
	@Override
	public List<Charter> getPiltoCharters(String pilotUuid) {
		TypedQuery<Charter> query = em.createQuery("SELECT x FROM Charter x JOIN x.pilot p WHERE p.uuid = :pilotUuid", getEntityClass());
		query.setParameter("pilotUuid", pilotUuid);
		List<Charter> results = query.getResultList();

		return Collections.unmodifiableList(results);
	}

}
