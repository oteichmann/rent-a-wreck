package com.prodyna.pac.rentawreck.backend.resttest.sample;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
//import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.rest.service.StringResponse;
import com.prodyna.pac.rentawreck.backend.rest.service.UtilService;
import com.prodyna.pac.rentawreck.backend.rest.service.impl.UtilServiceBean;
import com.prodyna.pac.rentawreck.backend.resttest.TestJaxRsActivator;

@RunWith(Arquillian.class)
public class ArquillianResteasyExtensionSampleTest {

	@ArquillianResource
	private URL deploymentURL;

	@Deployment(testable = false)
	public static WebArchive create() {
		WebArchive wa = ShrinkWrap.create(WebArchive.class, "raw-test.war");
		wa.addClasses(TestJaxRsActivator.class, UtilService.class, UtilServiceBean.class, StringResponse.class);
		return wa;
	}

	/**
	 * Arquillian calculates resource path by using deployment URL+ArquillianResteasyResource.value which is by default
	 * "rest". If your API is located under different root i.e. "api_v2" then use @ArquillianResteasyResource("api_v2")
	 *
	 * @param customerResource
	 *            configured resource ready for use, injected by Arquillian
	 */
	@Test
	public void generateUuid(@ArquillianResteasyResource UtilService utilService) {
		StringResponse response = utilService.gernerateUUID();

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getValue());
		System.out.println(response.getValue());
	}
}