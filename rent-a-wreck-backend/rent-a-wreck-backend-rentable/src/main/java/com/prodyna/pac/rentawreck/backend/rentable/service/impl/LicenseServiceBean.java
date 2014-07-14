package com.prodyna.pac.rentawreck.backend.rentable.service.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.prodyna.pac.rentawreck.backend.common.monitoring.Monitored;
import com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean;
import com.prodyna.pac.rentawreck.backend.rentable.model.Charter;
import com.prodyna.pac.rentawreck.backend.rentable.model.License;
import com.prodyna.pac.rentawreck.backend.rentable.service.LicenseService;

/**
 * Implementation of the {@link LicenseService} interface.
 *
 * @author Oliver Teichmann
 *
 */
@Stateless
@Monitored
public class LicenseServiceBean extends AbstractEntityPersistenceServiceBean<License> implements LicenseService {

	@Inject
	private Logger logger;
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#getEntityClass()
	 */
	@Override
	protected Class<License> getEntityClass() {
		return License.class;
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
		return License.NQ_FIND_ALL;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#getFindAllCountNamedQuery()
	 */
	@Override
	protected String getFindAllCountNamedQuery() {
		return License.NQ_FIND_ALL_COUNT;
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#create(java.lang.String, com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity)
	 */
	@Override
	public License create(String uuid, License entity) {
		fixDates(entity);
		return super.create(uuid, entity);
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#create(com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity)
	 */
	@Override
	public License create(License entity) {
		fixDates(entity);
		return super.create(entity);
	}
	
	/**
	 * Utility method to set date times. Required as client can not maintain times.
	 * @param license
	 */
	private License fixDates(License license) {
		
		GregorianCalendar endDate = new GregorianCalendar();
		endDate.setTime(license.getValidTill());
		endDate.set(Calendar.HOUR_OF_DAY, 23);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		
		license.setValidTill(endDate.getTime());
		
		return license;
	}
	

}
