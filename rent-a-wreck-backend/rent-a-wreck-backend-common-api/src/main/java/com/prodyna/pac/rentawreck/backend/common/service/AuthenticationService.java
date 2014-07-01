package com.prodyna.pac.rentawreck.backend.common.service;

import javax.security.auth.login.LoginException;

import org.jboss.ejb3.annotation.SecurityDomain;

import com.prodyna.pac.rentawreck.backend.common.model.TokenSubject;

/**
 * AuthenticationService
 *
 * @author Oliver Teichmann
 *
 */
@SecurityDomain("rent-a-wreck")
public interface AuthenticationService {
	
	public static final String X_XSRF_TOKEN = "X-XSRF-TOKEN";
	public static final String XSRF_TOKEN = "XSRF-TOKEN";
	
	public TokenSubject login(String username, String password) throws LoginException;
	
	public TokenSubject login(String token) throws LoginException;
	
	public boolean logout(String token);

//	public TokenSubject getTokenSubject(String token);

}
