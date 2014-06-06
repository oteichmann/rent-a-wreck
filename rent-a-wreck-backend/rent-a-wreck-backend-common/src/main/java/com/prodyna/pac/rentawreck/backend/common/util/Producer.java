package com.prodyna.pac.rentawreck.backend.common.util;

import java.lang.management.ManagementFactory;
import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.management.MBeanServer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence context, to CDI beans.
 * 
 * <p>
 * Example injection on a managed bean field:
 * </p>
 * 
 * <pre>
 * &#064;Inject
 * private Logger log;
 * </pre>
 *
 * @author Oliver Teichmann
 *
 */
public class Producer {

	@Produces
	@PersistenceContext
	private EntityManager em;

	@Produces
	public Logger produceLog(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}
	
	@Produces
	public MBeanServer produceMBeanServer() {
		return ManagementFactory.getPlatformMBeanServer();
	}

}
