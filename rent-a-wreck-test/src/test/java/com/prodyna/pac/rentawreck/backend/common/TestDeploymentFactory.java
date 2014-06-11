/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.common;

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
    
	private final WebArchive backendCommonDeployment;
	private final WebArchive backendRentableDeployment;
	
	private TestDeploymentFactory() {
		this.backendCommonDeployment = createBackendCommonDeployment();
		this.backendRentableDeployment = createBackendRentableDeployment();
	}
    
	public static TestDeploymentFactory getInstance() {
        return instance;
    }
	
	private WebArchive createBackendCommonDeployment() {
		WebArchive wa = ShrinkWrap.create(WebArchive.class, "raw-test.war");
		wa.addPackages(true, "com.prodyna.pac.rentawreck.backend.common");
		wa.deletePackage("com.prodyna.pac.rentawreck.backend.common.monitoring");
		wa.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
		wa.addAsResource("META-INF/beans.xml");
		wa.addAsWebInfResource("test-ds.xml", "test-ds.xml");
		System.out.println(wa.toString(true));

		return wa;
	}
	
	private WebArchive createBackendRentableDeployment() {
		WebArchive wa = ShrinkWrap.create(WebArchive.class, "raw-test.war");
		wa.addPackages(true, "com.prodyna.pac.rentawreck.backend.common",  "com.prodyna.pac.rentawreck.backend.rentable");
		wa.deletePackage("com.prodyna.pac.rentawreck.backend.common.monitoring");
		wa.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
		wa.addAsResource("META-INF/beans.xml");
		wa.addAsWebInfResource("test-ds.xml", "test-ds.xml");
		System.out.println(wa.toString(true));     
		
		return wa;
	}

	public WebArchive getBackendCommonDeployment() {
		return backendCommonDeployment;
	}

	public WebArchive getBackendRentableDeployment() {
		return backendRentableDeployment;
	}

}
