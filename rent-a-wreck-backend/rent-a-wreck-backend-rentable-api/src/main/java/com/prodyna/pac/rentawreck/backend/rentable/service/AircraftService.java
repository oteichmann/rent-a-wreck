/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.service;

import javax.ws.rs.Path;

import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.rentable.model.Aircraft;

/**
 * @author oteichmann
 */
@Path("/aircraft")
public interface AircraftService extends AbstractEntityPersistenceService<Aircraft> {

}
