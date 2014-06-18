/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.service;

import javax.ws.rs.Path;

import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.rentable.model.Charter;

/**
 * CharterService
 *
 * @author Oliver Teichmann
 *
 */
@Path("/charter")
public interface CharterService extends AbstractEntityPersistenceService<Charter> {

}
