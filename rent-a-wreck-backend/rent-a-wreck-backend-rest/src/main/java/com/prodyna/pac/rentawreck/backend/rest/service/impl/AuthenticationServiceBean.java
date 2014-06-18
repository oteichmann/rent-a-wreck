package com.prodyna.pac.rentawreck.backend.rest.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.picketbox.util.StringUtil;

import com.prodyna.pac.rentawreck.backend.common.model.User;
import com.prodyna.pac.rentawreck.backend.common.service.UserService;
import com.prodyna.pac.rentawreck.backend.rest.model.TokenSubject;
import com.prodyna.pac.rentawreck.backend.rest.service.AuthenticationService;
import com.prodyna.pac.rentawreck.backend.rest.service.AuthenticationServiceConstants;
import com.prodyna.pac.rentawreck.backend.rest.service.request.LoginRequest;
import com.prodyna.pac.rentawreck.backend.rest.service.request.LogoutRequest;
import com.prodyna.pac.rentawreck.backend.rest.service.request.ValidateTokenRequest;
import com.prodyna.pac.rentawreck.backend.rest.util.AuthenticationUtil;
import com.prodyna.pac.rentawreck.backend.rest.util.ResponseMessageBuilder;

@ApplicationScoped
public class AuthenticationServiceBean implements AuthenticationService {
	
	@Inject
	private UserService userService;
	
	private Map<String, TokenSubject> tokenSubjectCache = new HashMap<String, TokenSubject>();

	@Override
	public Response login(LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();
		
        Subject subject = null;
		if (StringUtil.isNotNull(username) && StringUtil.isNotNull(password)) {
			try {
				subject = AuthenticationUtil.authenticateUser(username, password);
			} catch (LoginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ResponseMessageBuilder.accessDenied().message(String.format("Authentication of user %s failed.", username)).build();
			}
		}

		Set<String> roles = null;
		if (subject != null) {
			roles = AuthenticationUtil.getRoles(subject);
		}
		
		String token = UUID.randomUUID().toString();
		
		if(StringUtil.isNotNull(username) && roles != null) {
			tokenSubjectCache.put(token, new TokenSubject(username, roles));
		}
		
		NewCookie cookie = new NewCookie(AuthenticationServiceConstants.XSRF_TOKEN, token, "/" , "localhost", 1, "no comment", Integer.MAX_VALUE / 2, new Date(Long.MAX_VALUE), true, false);
		
		User user = userService.findByUsername(username);
		
//		ResponseMessageBuilder responseMessageBuilder = ResponseMessageBuilder.ok()
//				.message(String.format("User %s has been successfully authenticated.", username))
//				.cookie(cookie);
		
//		Response response = responseMessageBuilder.build();
		
		Response response = Response.ok().cookie(cookie).entity(user).build();
		
		response.getHeaders().add(AuthenticationServiceConstants.X_XSRF_TOKEN, token);
		
		return response;
	}
	
	@Override
	public TokenSubject getTokenSubject(String token) {
		return tokenSubjectCache.get(token);
	}

	@Override
	public Response validateToken(ValidateTokenRequest validateTokenRequest) {
		
		if(tokenSubjectCache.containsKey(validateTokenRequest.getToken())) {
			return ResponseMessageBuilder.ok().message("Token is valid").build();
		}
		return ResponseMessageBuilder.authenticationRequired().message("Token is not valid.").build();
	}

	@Override
	public Response logout(LogoutRequest logoutRequest) {

		if(tokenSubjectCache.containsKey(logoutRequest.getToken())) {
			tokenSubjectCache.remove(logoutRequest.getToken());
			return ResponseMessageBuilder.ok().message("Token has been removed. Logout successfull.").build();
		}
		return ResponseMessageBuilder.authenticationRequired().message("Token is not valid. Logout not possible.").build();
	}


	
}
