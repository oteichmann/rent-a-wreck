package com.prodyna.pac.rentawreck.backend.common.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TokenSubject implements Serializable {
	
	private static final long serialVersionUID = -5045644046212078643L;
	
	private String token;
	private User user;
	private String password;
	private Set<String> roleNames = new HashSet<String>();
	
	public TokenSubject(String token, User user, String password) {
		this.token = token;
		this.user = user;
		this.password = password;
		
		for (Role role : user.getRoles()) {
			this.roleNames.add(role.getName());
		}
	}

	public String getToken() {
		return token;
	}

	public User getUser() {
		return user;
	}

	public String getUsername() {
		return user.getUsername();
	}
	
	public String getPassword() {
		return password;
	}
	
	public Set<String> getRoleNames() {
		return Collections.unmodifiableSet(this.roleNames);
	}


}
