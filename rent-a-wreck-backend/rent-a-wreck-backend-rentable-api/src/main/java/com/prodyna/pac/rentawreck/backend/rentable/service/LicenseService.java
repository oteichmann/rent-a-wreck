/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.rentable.model.License;

/**
 * Service interface for the {@link License} entity.
 *
 * @author Oliver Teichmann
 *
 */
@Path("/license")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface LicenseService extends AbstractEntityPersistenceService<License> {

}
