package com.prodyna.pac.rentawreck.backend.rentable.service.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;

import com.prodyna.pac.rentawreck.backend.common.monitoring.Monitored;
import com.prodyna.pac.rentawreck.backend.common.service.exception.ValidationException;
import com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean;
import com.prodyna.pac.rentawreck.backend.rentable.model.AircraftType;
import com.prodyna.pac.rentawreck.backend.rentable.model.Charter;
import com.prodyna.pac.rentawreck.backend.rentable.model.CharterStatus;
import com.prodyna.pac.rentawreck.backend.rentable.model.License;
import com.prodyna.pac.rentawreck.backend.rentable.model.Pilot;
import com.prodyna.pac.rentawreck.backend.rentable.service.CharterService;
import com.prodyna.pac.rentawreck.backend.rentable.service.PilotService;

/**
 * Implementation of the {@link CharterService} interface.
 *
 * @author Oliver Teichmann
 *
 */
@Stateless
@Monitored
public class CharterServiceBean extends AbstractEntityPersistenceServiceBean<Charter> implements CharterService {
	
	@Resource
	private SessionContext sessionContext;
	
	@Inject
	private Logger logger;
	
	@Inject 
	private PilotService pilotService;
	
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
		return logger;
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
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#create(java.lang.String, com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity)
	 */
	@Override
	public Charter create(String uuid, Charter charter) {
		Pilot pilot = pilotService.read(charter.getPilot().getUuid());
		return createCharter(charter, pilot);
	}


	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.rentable.service.CharterService#createCharterForPilot(java.lang.String, com.prodyna.pac.rentawreck.backend.rentable.model.Charter)
	 */
	@Override
	public Charter createCharter(String uuid, Charter charter) {

		Pilot pilot = pilotService.read(charter.getPilot().getUuid());
		
		// Validate permission
		validatePermission(pilot);
		
		return createCharter(charter, pilot);
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.rentable.service.CharterService#updatePilotCharterDates(java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public Charter updateCharterDates(String uuid, Date charterStart, Date charterEnd) {
		logger.debug("updatePilotCharterDates");
		
		// Validate date range
		if(!charterEnd.after(charterStart)) {
			throw new ValidationException("The end date of the charter must be after the start date.");
		}
		
		Charter charter = read(uuid);
		
		// Validate permission
		validatePermission(charter);
		
		charter.setCharterStart(charterStart);
		charter.setCharterEnd(charterEnd);
		
		fixDates(charter);
		
		return update(charter);
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.rentable.service.CharterService#updatePilotCharterStatus(java.lang.String, com.prodyna.pac.rentawreck.backend.rentable.model.CharterStatus)
	 */
	@Override
	public Charter updateCharterStatus(String uuid, CharterStatus newCharterStatus) {
		logger.debug("updatePilotCharterStatus");
		
		if(newCharterStatus == null) {
			throw new ValidationException("New status is missing.");
		}
		
		Charter charter = read(uuid);
		
		// Validate permission
		validatePermission(charter);
		
		if ((newCharterStatus.equals(CharterStatus.LENT) && charter.getCharterStatus().equals(CharterStatus.RESERVED)) || 
				(newCharterStatus.equals(CharterStatus.RETURNED) && charter.getCharterStatus().equals(CharterStatus.LENT)) || 
				(newCharterStatus.equals(CharterStatus.CANCELED) && charter.getCharterStatus().equals(CharterStatus.RESERVED))) {
			
			Date now = new Date();
			if(newCharterStatus.equals(CharterStatus.LENT)) {
				if(charter.getCharterStart().after(now) ||  charter.getCharterEnd().before(now)) {
					throw new ValidationException("Aircraft can only be lent during reservation time.");
				}				
			}
			charter.setCharterStatus(newCharterStatus);
			return update(charter);
		} else {
			throw new ValidationException("Invalid state trasistion of charter.");
		}
		
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
		TypedQuery<Charter> query = em.createQuery("SELECT c FROM Charter c JOIN c.aircraft a WHERE c.charterStart <= :today AND c.charterEnd >= :today AND a.uuid = :aircraftUuid AND c.charterStatus IN (:activeCharterStatusList)", getEntityClass());
		query.setParameter("today", now, TemporalType.TIMESTAMP);
		query.setParameter("aircraftUuid", aircraftUuid);
		query.setParameter("activeCharterStatusList", Arrays.asList(new CharterStatus[] {CharterStatus.RESERVED, CharterStatus.LENT}));
		
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
	
	private void validatePermission(Pilot pilot) {
		String callerPrincipal = sessionContext.getCallerPrincipal().getName();
		String pilotUsername = pilot.getUser().getUsername();

		if(!callerPrincipal.equalsIgnoreCase(pilotUsername)) {
			throw new ValidationException("You are not allowed to create charters for other pilots than your own.");
		}
	}

	private void validatePermission(Charter charter) {
		Pilot pilot = pilotService.read(charter.getPilot().getUuid());
		
		validatePermission(pilot);
	}

	private Charter createCharter(Charter charter, Pilot pilot) {
		
		// Validate status
		if(!charter.getCharterStatus().equals(CharterStatus.RESERVED)) {
			throw new ValidationException("Invalid status for new charter.");
		}
		
		// Validate date range
		fixDates(charter);
		if(!charter.getCharterEnd().after(charter.getCharterStart())) {
			throw new ValidationException("The end date of the charter must be after the start date.");
		}
		
		// Check if aircraft is available
		Integer aircraftChartersInDateRangeCount = getActiveAircraftChartersInDateRangeCount(charter.getAircraft().getUuid(), charter.getCharterStart(), charter.getCharterEnd());
		if (aircraftChartersInDateRangeCount > 0) {
			throw new ValidationException("The chosen aircraft is already chartered in the selected date range.");
		}
		
		// Validate license
		AircraftType aircraftType = charter.getAircraft().getType();
		boolean hasValidLicense = false;
		for (License license : pilot.getLicenses()) {
			if(license.getAircraftType().equals(aircraftType) && license.getValidTill().after(charter.getCharterEnd())) {
				hasValidLicense = true;
				break;
			}
		}
		if (!hasValidLicense) {
			throw new ValidationException("Pilot has no valid license for the selected aircraft type.");
		}
		
		return create(charter);
	}
	
	private Integer getActiveAircraftChartersInDateRangeCount(String aircraftUuid, Date startDate, Date endDate) {
		//SELECT * FROM raw_charters c LEFT JOIN raw_aircrafts a ON c.aircraft = a.uuid WHERE a.uuid = '946b2c7d-fc21-4926-a9a3-ccedc1cc63b4' AND ( c.charter_start BETWEEN '2014-07-04' AND '2014-07-06' OR c.charter_end BETWEEN '2014-07-04 00:00:00' AND '2014-07-05 01:22:39'
		TypedQuery<Number> query = em.createQuery("SELECT COUNT(c.uuid) FROM Charter c JOIN c.aircraft a WHERE a.uuid = :aircraftUuid AND c.charterStatus IN (:activeCharterStatusList) AND ( c.charterStart BETWEEN :startDate AND :endDate OR c.charterEnd BETWEEN :startDate AND :endDate)", Number.class);
		query.setParameter("aircraftUuid", aircraftUuid);
		query.setParameter("activeCharterStatusList", Arrays.asList(new CharterStatus[] {CharterStatus.RESERVED, CharterStatus.LENT}));
		query.setParameter("startDate", startDate, TemporalType.TIMESTAMP);
		query.setParameter("endDate", endDate, TemporalType.TIMESTAMP);
		
		return query.getSingleResult().intValue();
	}


	/**
	 * Utility method to set date times. Required as client can not maintain times.
	 * @param charter
	 */
	private Charter fixDates(Charter charter) {
		
		GregorianCalendar endDate = new GregorianCalendar();
		endDate.setTime(charter.getCharterEnd());
		endDate.set(Calendar.HOUR_OF_DAY, 23);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		
		charter.setCharterEnd(endDate.getTime());
		
		GregorianCalendar startDate = new GregorianCalendar();
		startDate.setTime(charter.getCharterStart());
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		
		charter.setCharterStart(startDate.getTime());
		
		return charter;
	}
}
