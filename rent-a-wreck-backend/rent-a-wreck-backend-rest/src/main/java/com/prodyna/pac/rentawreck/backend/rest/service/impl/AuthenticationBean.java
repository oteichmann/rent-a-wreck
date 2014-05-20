package com.prodyna.pac.rentawreck.backend.rest.service.impl;

import java.util.UUID;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

import com.prodyna.pac.rentawreck.backend.rest.service.Authetication;

@ApplicationScoped
public class AuthenticationBean implements Authetication {
	
//    @Resource
//    private SessionContext context;
	
	public Response login() {
		
//		StringBuilder sb = new StringBuilder();
//		sb.append("Token assigned for user: ").append(context.getCallerPrincipal().getName());
		String token = UUID.randomUUID().toString();
		Response response = MessageBuilder.ok().token(token).message().build();
		response.getHeaders().add("X-XSRF-TOKEN", token);
		
		return response;
	}

}
