package com.prodyna.pac.rentawreck.backend.rest.service.request;

import java.io.Serializable;

public class LogoutRequest implements Serializable {

	private static final long serialVersionUID = 4972059447417760758L;
	
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
