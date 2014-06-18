/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.service;

import javax.ws.rs.Path;

import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.rentable.model.Pilot;

/**
 * PilotService
 *
 * @author Oliver Teichmann
 *
 */
@Path("/pilot")
public interface PilotService extends AbstractEntityPersistenceService<Pilot> {

}
