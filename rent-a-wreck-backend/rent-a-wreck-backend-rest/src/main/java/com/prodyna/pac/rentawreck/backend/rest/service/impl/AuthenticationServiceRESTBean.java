package com.prodyna.pac.rentawreck.backend.rest.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import com.prodyna.pac.rentawreck.backend.common.model.TokenSubject;
import com.prodyna.pac.rentawreck.backend.common.model.User;
import com.prodyna.pac.rentawreck.backend.common.service.AuthenticationService;
import com.prodyna.pac.rentawreck.backend.rest.service.AuthenticationServiceREST;
import com.prodyna.pac.rentawreck.backend.rest.service.request.LoginRequest;
import com.prodyna.pac.rentawreck.backend.rest.service.request.TokenRequest;
import com.prodyna.pac.rentawreck.backend.rest.util.AuthenticationCookieUtil;
import com.prodyna.pac.rentawreck.backend.rest.util.ResponseMessageBuilder;

@Stateless
public class AuthenticationServiceRESTBean implements AuthenticationServiceREST {
	
	@Inject
	private AuthenticationService authenticationService;
	
	@Override
	public Response login(LoginRequest loginRequest) {
		
		TokenSubject tokenSubject = null;
		try {
			tokenSubject = authenticationService.login(loginRequest.getUsername(), loginRequest.getPassword());
		} catch (LoginException e) {
			return ResponseMessageBuilder.accessDenied().cookie(AuthenticationCookieUtil.createInvalidCookie()).message(String.format("Authentication of user %s failed.", loginRequest.getUsername())).build();
		}
		
		Response response = createLoginSuccessResponse(tokenSubject);
		
//		response.getHeaders().add(AuthenticationServiceConstants.X_XSRF_TOKEN, token);
		
		return response;
	}

	/**
	 * @param tokenSubject
	 * @return
	 */
	private Response createLoginSuccessResponse(TokenSubject tokenSubject) {
		String token = tokenSubject.getToken();

		NewCookie cookie = AuthenticationCookieUtil.createTokenCookie(token);
		
		User user = tokenSubject.getUser();
		
		Response response = Response.ok().cookie(cookie).entity(user).build();
		
		return response;
	}
	
	@Override
	public Response validateToken(TokenRequest tokenRequest) {
		
		TokenSubject tokenSubject = null;
		try {
			tokenSubject = authenticationService.login(tokenRequest.getToken());
		} catch (LoginException e) {
			return ResponseMessageBuilder.authenticationRequired().cookie(AuthenticationCookieUtil.createInvalidCookie()).message("Token is not valid!").build();
		}
		
		Response response = createLoginSuccessResponse(tokenSubject);
		return response;
	}

	@Override
	public Response logout(TokenRequest tokenRequest) {

		NewCookie invalidCookie = AuthenticationCookieUtil.createInvalidCookie();
		try {
			authenticationService.logout(tokenRequest.getToken());
			return ResponseMessageBuilder.ok().cookie(invalidCookie).message("Token has been removed. Logout successfull.").build();
		} catch(Exception e) {
			return ResponseMessageBuilder.authenticationRequired().cookie(invalidCookie).message("Token is not valid. Logout not possible.").build();
		}
	}




	
}
