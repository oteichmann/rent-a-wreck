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
	
	/**
	 * Login by username and password.
	 * @param username
	 * @param password
	 * @return The {@link TokenSubject} containing the authenticated user.
	 * @throws LoginException If authentication via username and password fails.
	 */
	public TokenSubject login(String username, String password) throws LoginException;
	
	/**
	 * Login by token.
	 * @param token
	 * @return The {@link TokenSubject} containing the authenticated user.
	 * @throws LoginException If authentication via token fails.
	 */
	public TokenSubject login(String token) throws LoginException;
	
	/**
	 * Logout the user identified by the given token. The given token is invalidated and can not be used for
	 * authentication anymore.
	 * @param token
	 * @return Boolean value. true if logout was successful - false if not.
	 */
	public boolean logout(String token);

}
