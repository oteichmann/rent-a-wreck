/*
 * JBoss, Home of Professional Open Source
 *
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.prodyna.pac.rentawreck.backend.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.ext.Provider;

import org.jboss.ejb3.annotation.Pool;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;
import org.jboss.resteasy.util.HttpHeaderNames;

import com.prodyna.pac.rentawreck.backend.rest.service.AuthenticationResponse;

/**
 * A {@link PostProcessInterceptor} that checks if we have an AuthenticationResponse
 * of success and then inserts cookie into the headers
 * @author anil saldhana
 * @since Jan 31, 2013
 */
@Provider
@AuthenticationResponse
public class PostLoginInterception implements ContainerResponseFilter {
//    private static final String AUTH_TOKEN_HEADER_NAME = "Auth-Token";
    
//    @Context ServletContext servletContext;
    
//    /**
//     * Put the Auth-Token in the cookie
//     */
//    @Override
//    public void postProcess(ServerResponse response) {
//        if(response != null){
//            Object entity = response.getEntity();
//            response.getHeaders().get(key)
//            if(entity instanceof AuthenticationResponse){
//                AuthenticationResponse authResponse = (AuthenticationResponse) entity;
//                if(authResponse.isLoggedIn()){
//                    String token = authResponse.getToken();
//                    //Now set the cookie
//                    MultivaluedMap<String, Object> headers = response.getMetadata();
//                    List<Object> cookies = headers.get(HttpHeaderNames.SET_COOKIE);
//                    if(cookies == null){
//                        cookies = new ArrayList<Object>();
//                    }
//                    
//                    String contextPath = servletContext.getContextPath();
//                    SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
//                    String path = sessionCookieConfig.getPath();
//                    String domain = sessionCookieConfig.getDomain();
//                    int age = sessionCookieConfig.getMaxAge();
//                    boolean secureCookie = sessionCookieConfig.isSecure();
//                    
//                    NewCookie cookie = new NewCookie(AUTH_TOKEN_HEADER_NAME, token,contextPath,path,domain,age,secureCookie);
//                    
//                    cookies.add(cookie.toString() + ";HttpOnly"); //Hacky way to introduce HttpOnly
//                    
//                    headers.put(HttpHeaderNames.SET_COOKIE, cookies);
//                }
//            }
//        }
//    }

	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		
		String token = (String) responseContext.getHeaders().getFirst("X-XSRF-TOKEN");
		
		if(token != null) {
			NewCookie cookie = new NewCookie("XSRF-TOKEN", token, "/" , "localhost", 1, "no comment", Integer.MAX_VALUE / 2, new Date(Long.MAX_VALUE), true, true);
			
			MultivaluedMap<String,Object> headers = responseContext.getHeaders();
			List<Object> cookies = headers.get(HttpHeaderNames.SET_COOKIE);
			if (cookies == null) {
				cookies = new ArrayList<Object>();
			}
			
			cookies.add(cookie.toString());
			
			headers.put(HttpHeaderNames.SET_COOKIE, cookies);
		}
		
	}
}