/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.service;

import javax.ws.rs.Path;

import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.rentable.model.License;

/**
 * Service interface for the {@link License} entity.
 *
 * @author Oliver Teichmann
 *
 */
@Path("/license")
public interface LicenseService extends AbstractEntityPersistenceService<License> {

}
