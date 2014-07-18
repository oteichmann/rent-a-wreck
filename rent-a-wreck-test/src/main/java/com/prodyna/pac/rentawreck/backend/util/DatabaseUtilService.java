package com.prodyna.pac.rentawreck.backend.util;

import java.util.List;

/**
 * DBUtilService
 *
 * @author Oliver Teichmann
 *
 */
public interface DatabaseUtilService {
	
    void executeDatabaseUtilScript(DatabaseUtilScript script);

    void executeDatabaseUtilScripts(List<DatabaseUtilScript> scripts);
}
