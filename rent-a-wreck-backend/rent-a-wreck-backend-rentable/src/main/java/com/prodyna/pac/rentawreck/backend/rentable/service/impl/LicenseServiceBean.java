package com.prodyna.pac.rentawreck.backend.rentable.service.impl;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.prodyna.pac.rentawreck.backend.common.monitoring.Monitored;
import com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean;
import com.prodyna.pac.rentawreck.backend.rentable.model.License;
import com.prodyna.pac.rentawreck.backend.rentable.service.LicenseService;

/**
 * LicenseServiceBean
 *
 * @author Oliver Teichmann
 *
 */
@Stateless
@Monitored
public class LicenseServiceBean extends AbstractEntityPersistenceServiceBean<License> implements LicenseService {

	@Inject
	private Logger log;
	
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
		return log;
	}

}
