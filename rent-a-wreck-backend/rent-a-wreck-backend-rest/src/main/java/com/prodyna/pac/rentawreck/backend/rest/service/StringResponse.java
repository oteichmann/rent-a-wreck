package com.prodyna.pac.rentawreck.backend.rest.service;

/**
 * StringResponse
 *
 * @author Oliver Teichmann, PRODYNA AG
 *
 */
public class StringResponse {
	
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
