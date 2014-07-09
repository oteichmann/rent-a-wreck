package com.prodyna.pac.rentawreck.dbutil;

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
