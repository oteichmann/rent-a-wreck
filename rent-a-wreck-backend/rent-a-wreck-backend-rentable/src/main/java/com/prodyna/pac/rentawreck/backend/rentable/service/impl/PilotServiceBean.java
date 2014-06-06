package com.prodyna.pac.rentawreck.backend.rentable.service.impl;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.prodyna.pac.rentawreck.backend.common.monitoring.Monitored;
import com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean;
import com.prodyna.pac.rentawreck.backend.rentable.model.Pilot;
import com.prodyna.pac.rentawreck.backend.rentable.service.PilotService;

/**
 * PilotServiceBean
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

}
