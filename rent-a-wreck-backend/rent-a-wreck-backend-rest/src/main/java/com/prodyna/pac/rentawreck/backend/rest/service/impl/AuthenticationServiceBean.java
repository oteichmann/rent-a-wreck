package com.prodyna.pac.rentawreck.backend.rest.service.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
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
		
		NewCookie cookie = createTokenCookie(token);
		
		User user = userService.findByUsername(username);
		
//		ResponseMessageBuilder responseMessageBuilder = ResponseMessageBuilder.ok()
//				.message(String.format("User %s has been successfully authenticated.", username))
//				.cookie(cookie);
		
//		Response response = responseMessageBuilder.build();
		
		Response response = Response.ok().cookie(cookie).entity(user).build();
		
		response.getHeaders().add(AuthenticationServiceConstants.X_XSRF_TOKEN, token);
		
		return response;
	}

	private NewCookie createTokenCookie(String token) {
		Calendar cookieExpiryDate = new GregorianCalendar();
		cookieExpiryDate.add(Calendar.MINUTE, 30);
		NewCookie cookie = new NewCookie(AuthenticationServiceConstants.XSRF_TOKEN, token, "/" , "localhost", 1, 
				"RAW Session Token", 1800, cookieExpiryDate.getTime(), true, false);
		return cookie;
	}
	
	@Override
	public TokenSubject getTokenSubject(String token) {
		return tokenSubjectCache.get(token);
	}

	@Override
	public Response validateToken(ValidateTokenRequest validateTokenRequest) {
		
		String token = validateTokenRequest.getToken();
		if(tokenSubjectCache.containsKey(token)) {

			String username = tokenSubjectCache.get(token).getUsername();
			NewCookie cookie = createTokenCookie(token);
			User user = userService.findByUsername(username);
			
			Response response = Response.ok().cookie().entity(user).build();
			return response;
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
