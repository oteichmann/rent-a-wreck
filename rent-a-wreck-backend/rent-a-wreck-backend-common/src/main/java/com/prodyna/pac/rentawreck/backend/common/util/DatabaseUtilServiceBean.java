package com.prodyna.pac.rentawreck.backend.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.prodyna.pac.rentawreck.backend.common.util.DatabaseUtilScript;
import com.prodyna.pac.rentawreck.backend.common.util.DatabaseUtilService;

/**
 * DBUtilService
 *
 * @author Oliver Teichmann
 *
 */
@Stateless
public class DatabaseUtilServiceBean implements DatabaseUtilService {

	@Inject
	private EntityManager em;

	@Inject
	private Logger logger;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.rentawreck.dbutil.DatabaseUtilService#executeDatabaseUtilScript(com.prodyna.pac.rentawreck.dbutil
	 * .DatabaseUtilScript)
	 */
	@Override
	public void executeDatabaseUtilScript(DatabaseUtilScript script) {
		List<DatabaseUtilScript> scripts = new ArrayList<DatabaseUtilScript>(1);
		scripts.add(script);
		executeDatabaseUtilScripts(scripts);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.rentawreck.dbutil.DatabaseUtilService#executeDatabaseUtilScripts(java.util.List)
	 */
	@Override
	public void executeDatabaseUtilScripts(List<DatabaseUtilScript> scripts) {
		try {

			for (DatabaseUtilScript script : scripts) {
				for (String sqlStatement : script.getSqlStatements()) {
					try {
						Query query = em.createNativeQuery(sqlStatement);
						query.executeUpdate();
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
			
			em.flush();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
