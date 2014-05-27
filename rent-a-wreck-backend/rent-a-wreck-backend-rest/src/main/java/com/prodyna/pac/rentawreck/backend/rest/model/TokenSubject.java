package com.prodyna.pac.rentawreck.backend.rest.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

public class TokenSubject implements Serializable {
	
	private static final long serialVersionUID = -5045644046212078643L;
	
	private String username;
	private Set<String> roles;
	
	public TokenSubject(String username, Set<String> roles) {
		super();
		this.username = username;
		this.roles = roles;
	}
	
	public String getUsername() {
		return username;
	}
	
	public Set<String> getRoles() {
		return Collections.unmodifiableSet(roles);
	}

}
