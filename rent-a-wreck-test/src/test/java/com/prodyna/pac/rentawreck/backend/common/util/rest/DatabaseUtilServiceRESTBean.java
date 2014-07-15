package com.prodyna.pac.rentawreck.backend.common.util.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.prodyna.pac.rentawreck.backend.common.util.DatabaseUtilScript;
import com.prodyna.pac.rentawreck.backend.common.util.DatabaseUtilService;
import com.prodyna.pac.rentawreck.backend.rest.util.ResponseMessageBuilder;

/**
 * DBUtilService
 *
 * @author Oliver Teichmann
 *
 */
@Stateless
public class DatabaseUtilServiceRESTBean implements DatabaseUtilServiceREST {

    @Inject
    private DatabaseUtilService databaseUtilService;

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.dbutil.DatabaseUtilService#executeDatabaseUtilScript(com.prodyna.pac.rentawreck.dbutil.DatabaseUtilScript)
	 */
	@Override
	public Response executeDatabaseUtilScript(DatabaseUtilScript script) {
		databaseUtilService.executeDatabaseUtilScript(script);
		
		return ResponseMessageBuilder.ok().message("Script executed").build();
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.dbutil.DatabaseUtilService#executeDatabaseUtilScripts(java.util.List)
	 */
	@Override
	public Response executeDatabaseUtilScripts(List<DatabaseUtilScript> scripts) {
		databaseUtilService.executeDatabaseUtilScripts(scripts);
		
		return ResponseMessageBuilder.ok().message("Script executed").build();
	}

}
