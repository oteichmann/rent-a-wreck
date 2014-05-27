package com.prodyna.pac.rentawreck.backend.rest.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class TokenRequest implements Serializable {

	private static final long serialVersionUID = 4972059447417760758L;
	
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
