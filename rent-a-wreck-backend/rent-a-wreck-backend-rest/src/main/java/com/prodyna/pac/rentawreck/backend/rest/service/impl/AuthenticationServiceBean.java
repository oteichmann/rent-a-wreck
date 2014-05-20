package com.prodyna.pac.rentawreck.backend.rest.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.ws.rs.core.Response;

import org.picketbox.util.StringUtil;

import com.prodyna.pac.rentawreck.backend.rest.model.AuthenticationRequest;
import com.prodyna.pac.rentawreck.backend.rest.model.TokenSubject;
import com.prodyna.pac.rentawreck.backend.rest.service.AuthenticationService;
import com.prodyna.pac.rentawreck.backend.rest.util.AuthenticationUtil;
import com.prodyna.pac.rentawreck.backend.rest.util.MessageBuilder;

@ApplicationScoped
public class AuthenticationServiceBean implements AuthenticationService {
	
	private Map<String, TokenSubject> tokenSubjectCache = new HashMap<String, TokenSubject>();

	@Override
	public Response login(AuthenticationRequest authenticationRequest) {
		String username = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();
		
        Subject subject = null;
		if (StringUtil.isNotNull(username) && StringUtil.isNotNull(password)) {
			try {
				subject = AuthenticationUtil.authenticateUser(username, password);
			} catch (LoginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return MessageBuilder.accessDenied().message(String.format("Authentication of user %s failed.", username)).build();
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
		
		Response response = MessageBuilder.ok().token(token).message(String.format("User %s has been successfully authenticated.", username)).build();
		response.getHeaders().add("X-XSRF-TOKEN", token);
		
		return response;
	}
	
	@Override
	public TokenSubject getTokenSubject(String token) {
		return tokenSubjectCache.get(token);
	}


	
}
