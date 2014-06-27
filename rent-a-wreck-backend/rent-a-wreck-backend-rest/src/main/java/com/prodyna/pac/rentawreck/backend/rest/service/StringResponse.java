package com.prodyna.pac.rentawreck.backend.rest.service;

import java.io.Serializable;

/**
 * StringResponse
 *
 * @author Oliver Teichmann, PRODYNA AG
 *
 */
public class StringResponse implements Serializable {
	
	private static final long serialVersionUID = 2526799471685871616L;

	public StringResponse() {
		// TODO Auto-generated constructor stub
	}
	
	String value;
	
	public StringResponse(String value) {
		this.value = value;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}
}
