package com.prodyna.pac.rentawreck.backend.rentable.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.prodyna.pac.rentawreck.backend.common.monitoring.Monitored;
import com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean;
import com.prodyna.pac.rentawreck.backend.rentable.model.Aircraft;
import com.prodyna.pac.rentawreck.backend.rentable.model.AircraftCharterStatus;
import com.prodyna.pac.rentawreck.backend.rentable.model.Charter;
import com.prodyna.pac.rentawreck.backend.rentable.service.AircraftService;
import com.prodyna.pac.rentawreck.backend.rentable.service.CharterService;

@Stateless
@Monitored
public class AircraftServiceBean extends AbstractEntityPersistenceServiceBean<Aircraft> implements AircraftService {

	@Inject
	private Logger log;

	@Inject
	private CharterService charterService;
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#getEntityClass()
	 */
	@Override
	protected Class<Aircraft> getEntityClass() {
		return Aircraft.class;
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
		return Aircraft.NQ_FIND_ALL;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#getFindAllCountNamedQuery()
	 */
	@Override
	protected String getFindAllCountNamedQuery() {
		return Aircraft.NQ_FIND_ALL_COUNT;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.rentable.service.ReservationService#getAircraftCharterStatusList()
	 */
	@Override
	public List<AircraftCharterStatus> getAircraftCharterStatusList() {
		
		List<AircraftCharterStatus> aircraftCharterStatusList = new ArrayList<AircraftCharterStatus>();
		
		List<Aircraft> aircrafts = this.findAll();
		
		for (Aircraft aircraft : aircrafts) {
			AircraftCharterStatus aircraftCharterStatus = new AircraftCharterStatus();
			
			aircraftCharterStatus.setAircraft(aircraft);
			
			Charter charter = charterService.getActiveAircraftCharter(aircraft.getUuid());
			
			if (charter != null) {
				aircraftCharterStatus.setCharter(charter);

				switch (charter.getCharterStatus()) {
				case LENT:
				case RESERVED:
					aircraftCharterStatus.setAvailable(false);
					break;
					
				default:
					aircraftCharterStatus.setAvailable(true);
					break;
				}
			} else {
				aircraftCharterStatus.setAvailable(true);	
			}
			
			aircraftCharterStatusList.add(aircraftCharterStatus);
		}
		return aircraftCharterStatusList;
	}
}
