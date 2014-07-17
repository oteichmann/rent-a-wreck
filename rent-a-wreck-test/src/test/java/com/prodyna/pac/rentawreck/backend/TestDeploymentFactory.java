/**
 * 
 */
package com.prodyna.pac.rentawreck.backend;

import javax.inject.Singleton;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import com.prodyna.pac.rentawreck.backend.rest.JaxRsActivator;
import com.prodyna.pac.rentawreck.backend.test.AbstractEntityCRUDTest;

/**
 * TestDeploymentFactory
 *
 * @author Oliver Teichmann
 *
 */
@Singleton
public class TestDeploymentFactory {
	
	private static TestDeploymentFactory instance = new TestDeploymentFactory();
    
	private final WebArchive backendAuthDeployment;
	private final WebArchive backendAuthDeploymentREST;
	private final WebArchive backendRentableDeployment;
	
	private TestDeploymentFactory() {
		this.backendAuthDeployment = createBackendAuthDeployment();
		this.backendAuthDeploymentREST = createBackendAuthDeploymentREST();
		this.backendRentableDeployment = createBackendRentableDeployment();
	}
    
	public static TestDeploymentFactory getInstance() {
        return instance;
    }
	
	private WebArchive createBackendAuthDeployment() {
		WebArchive wa = ShrinkWrap.create(WebArchive.class, "raw-test.war");
		wa.addPackages(true, "com.prodyna.pac.rentawreck.backend.common");
		wa.deletePackage("com.prodyna.pac.rentawreck.backend.common.monitoring");
		wa.addPackages(true, "com.prodyna.pac.rentawreck.backend.auth");
		wa.addClass(AbstractEntityCRUDTest.class);
		wa.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
		wa.addAsResource("META-INF/beans.xml");
		wa.addAsWebInfResource("test-ds.xml", "test-ds.xml");

		return wa;
	}
	
	private WebArchive createBackendAuthDeploymentREST() {
		WebArchive wa = createBackendAuthDeployment();
		wa.addPackages(true, "com.prodyna.pac.rentawreck.backend.rest");
		wa.deleteClass(JaxRsActivator.class);
		wa.addPackages(false, "com.prodyna.pac.rentawreck.backend.resttest");
		wa.addAsWebInfResource("test-security-domain.xml", "test-security-domain.xml");
		
		return wa;
	}
	
	private WebArchive createBackendRentableDeployment() {
		WebArchive wa = createBackendAuthDeployment();
		wa.addPackages(true, "com.prodyna.pac.rentawreck.backend.rentable");
		
		return wa;
	}

	public WebArchive getBackendAuthDeployment() {
		return backendAuthDeployment;
	}

	public WebArchive getBackendRentableDeployment() {
		return backendRentableDeployment;
	}

	public WebArchive getBackendAuthDeploymentREST() {
		return backendAuthDeploymentREST;
	}

}
