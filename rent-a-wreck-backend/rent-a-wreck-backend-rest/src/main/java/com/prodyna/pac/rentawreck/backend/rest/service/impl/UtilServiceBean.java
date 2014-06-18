package com.prodyna.pac.rentawreck.backend.rest.service.impl;

import java.util.UUID;

import javax.ejb.Stateless;

import com.prodyna.pac.rentawreck.backend.rest.service.StringResponse;
import com.prodyna.pac.rentawreck.backend.rest.service.UtilService;

/**
 * UtilServiceBean
 *
 * @author Oliver Teichmann, PRODYNA AG
 *
 */
@Stateless
public class UtilServiceBean implements UtilService {

	/* (non-Javadoc)
	 * @see de.gopa.meq.service.UtilService#gernerateUUID()
	 */
	@Override
	public StringResponse gernerateUUID() {
		return new StringResponse(UUID.randomUUID().toString());
	}

}
