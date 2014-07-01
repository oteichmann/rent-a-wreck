package com.prodyna.pac.rentawreck.backend.rentable.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import com.prodyna.pac.rentawreck.backend.common.monitoring.Monitored;
import com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean;
import com.prodyna.pac.rentawreck.backend.rentable.model.Charter;
import com.prodyna.pac.rentawreck.backend.rentable.model.CharterStatus;
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
	
	@Resource
	private SessionContext sessionContext;
	
	@Resource
	private EJBContext ctx;
	
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
		
		log.info("EJBContext: " + ctx.toString());
		log.info("SessionContext: " + sessionContext.toString());

		log.info("SessionContext: " + sessionContext.getCallerPrincipal().getName());
		
		return Collections.unmodifiableList(results);
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.rentable.service.CharterService#getPiltoCharters(java.lang.String)
	 */
	@Override
	public List<Charter> getPilotCharters(String pilotUuid) {
		TypedQuery<Charter> query = em.createQuery("SELECT c FROM Charter c JOIN c.pilot p WHERE p.uuid = :pilotUuid", getEntityClass());
		query.setParameter("pilotUuid", pilotUuid);
		List<Charter> results = query.getResultList();
		
		return Collections.unmodifiableList(results);
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.rentable.service.CharterService#getAircraftCharters(java.lang.String)
	 */
	@Override
	public Charter getActiveAircraftCharter(String aircraftUuid) {
		Date now = new Date();
		TypedQuery<Charter> query = em.createQuery("SELECT c FROM Charter c JOIN c.aircraft a WHERE c.charterStart <= :today AND c.charterEnd >= :today AND a.uuid = :aircraftUuid", getEntityClass());
		query.setParameter("today", now, TemporalType.TIMESTAMP);
		query.setParameter("aircraftUuid", aircraftUuid);
		
		try {
			Charter charter = query.getSingleResult();
			return charter;
		} catch (NoResultException e) {
			return null;
		}
	}


	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.rentable.service.CharterService#getOverdueCharters()
	 */
	@Override
	public List<Charter> getOverdueCharters() {
		Date now = new Date();
		TypedQuery<Charter> query = em.createQuery("SELECT c FROM Charter c WHERE c.charterEnd < :today AND c.charterStatus = :status", getEntityClass());
		query.setParameter("today", now, TemporalType.TIMESTAMP);
		query.setParameter("status", CharterStatus.LENT);
				
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.rentable.service.CharterService#createCharterForPilot(java.lang.String, com.prodyna.pac.rentawreck.backend.rentable.model.Charter)
	 */
	@Override
	public Charter createCharterForPilot(String uuid, Charter charter) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.rentable.service.CharterService#updatePilotCharterDates(java.lang.String, com.prodyna.pac.rentawreck.backend.rentable.model.Charter)
	 */
	@Override
	public Charter updatePilotCharterDates(String uuid, Charter charter) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
