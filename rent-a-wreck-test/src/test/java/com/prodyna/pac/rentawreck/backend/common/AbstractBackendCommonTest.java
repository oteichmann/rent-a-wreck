/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.common;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity;

/**
 * AbstractBackendCommonTest
 *
 * @author Oliver Teichmann
 *
 */
public abstract class AbstractBackendCommonTest<T extends AbstractEntity> extends AbstractEntityCRUDTest<T>{

//	@Deployment
//	public static WebArchive createDeployment() {
//		WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war");
//		wa.addPackages(true, "com.prodyna.pac.rentawreck.backend.common");
//		wa.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
//		wa.addAsResource("META-INF/beans.xml");
//		wa.addAsWebInfResource("test-ds.xml", "test-ds.xml");
//		System.out.println(wa.toString(true));
//
//		return wa;
//	}

}
