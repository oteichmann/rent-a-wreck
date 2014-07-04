package com.prodyna.pac.rentawreck.backend.common.service.rest;

import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.resteasy.spi.StringParameterUnmarshaller;
import org.jboss.resteasy.util.FindAnnotation;

/**
 * Implementation class to unmarshall RESTeasy request parameters to Date objects.
 *
 * @author Oliver Teichmann
 *
 */
public class DateFormatter implements StringParameterUnmarshaller<Date> {
	private SimpleDateFormat formatter;

	public void setAnnotations(Annotation[] annotations) {
		DateFormat format = FindAnnotation.findAnnotation(annotations, DateFormat.class);
		formatter = new SimpleDateFormat(format.value());
	}

	public Date fromString(String str) {
		try {
			return formatter.parse(str);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}