/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.service;

import java.util.List;

import javax.ws.rs.Path;

import com.prodyna.pac.rentawreck.backend.rentable.model.AircraftCharterStatus;
import com.prodyna.pac.rentawreck.backend.rentable.model.Charter;

/**
 * @author oteichmann
 */
@Path("/reservation")
public interface ReservationService {
	
	List<AircraftCharterStatus> getAircraftCharterStatusList();

}
