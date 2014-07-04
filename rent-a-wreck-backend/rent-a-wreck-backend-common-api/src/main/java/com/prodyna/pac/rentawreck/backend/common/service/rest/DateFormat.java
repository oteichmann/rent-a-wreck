package com.prodyna.pac.rentawreck.backend.common.service.rest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.jboss.resteasy.annotations.StringParameterUnmarshallerBinder;

/**
 * Annotation to format RESTeasy request parameters to date.
 *
 * @author Oliver Teichmann
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@StringParameterUnmarshallerBinder(DateFormatter.class)
public @interface DateFormat {
	String value();
}
