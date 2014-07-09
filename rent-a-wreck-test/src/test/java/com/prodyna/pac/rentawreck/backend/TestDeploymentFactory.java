/**
 * 
 */
package com.prodyna.pac.rentawreck.backend;

import javax.inject.Singleton;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

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
	private final WebArchive backendRentableDeployment;
	
	private TestDeploymentFactory() {
		this.backendAuthDeployment = createBackendAuthDeployment();
		this.backendRentableDeployment = createBackendRentableDeployment();
	}
    
	public static TestDeploymentFactory getInstance() {
        return instance;
    }
	
	private WebArchive createBackendCommonDeploymentBKP() {
		WebArchive wa = ShrinkWrap.create(WebArchive.class, "raw-test.war");
		wa.addPackages(true, "com.prodyna.pac.rentawreck.backend.common");
		wa.deletePackage("com.prodyna.pac.rentawreck.backend.common.monitoring");
		wa.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
		wa.addAsResource("META-INF/beans.xml");
		wa.addAsWebInfResource("test-ds.xml", "test-ds.xml");

		return wa;
	}
	
	private WebArchive createBackendAuthDeployment() {
		WebArchive wa = ShrinkWrap.create(WebArchive.class, "raw-test.war");
		wa.addPackages(true, "com.prodyna.pac.rentawreck.backend.common");
		wa.deletePackage("com.prodyna.pac.rentawreck.backend.common.monitoring");
		wa.addPackages(true, "com.prodyna.pac.rentawreck.backend.auth");
		wa.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
		wa.addAsResource("META-INF/beans.xml");
		wa.addAsWebInfResource("test-ds.xml", "test-ds.xml");

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

}
