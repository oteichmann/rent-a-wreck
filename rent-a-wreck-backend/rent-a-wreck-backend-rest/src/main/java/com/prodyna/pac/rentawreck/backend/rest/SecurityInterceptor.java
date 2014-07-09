package com.prodyna.pac.rentawreck.backend.rest;
 
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.util.Base64;
import org.picketbox.util.StringUtil;
import org.slf4j.Logger;

import com.prodyna.pac.rentawreck.backend.common.model.TokenSubject;
import com.prodyna.pac.rentawreck.backend.common.service.AuthenticationService;
import com.prodyna.pac.rentawreck.backend.rest.service.AuthenticationServiceConstants;
import com.prodyna.pac.rentawreck.backend.rest.util.AuthenticationCookieUtil;
import com.prodyna.pac.rentawreck.backend.rest.util.ResponseMessageBuilder;

/**
 * This interceptor verify the access permissions for a user
 * based on username and passowrd provided in request
 * */ 
@Provider
public class SecurityInterceptor implements javax.ws.rs.container.ContainerRequestFilter {
	
	@Inject
	private Logger logger;
	
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";

	@Inject
	private AuthenticationService authenticationService;
     
    @Override
    public void filter(ContainerRequestContext requestContext) {
    	
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();
        //Access allowed for all
        if(!method.isAnnotationPresent(PermitAll.class))
        {
            //Access denied for all
            if(method.isAnnotationPresent(DenyAll.class))
            {
                requestContext.abortWith(ResponseMessageBuilder.accessDenied().message("Nobody can access this resource.").build());
                return;
            }
             
            //Verify user access
            if(method.isAnnotationPresent(RolesAllowed.class))
            {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> annotatedRoles = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
                
            	TokenSubject tokenSubject;
        		try {
        			tokenSubject = authenticate(requestContext);
        		} catch (LoginException e) {
        			NewCookie cookie = AuthenticationCookieUtil.createInvalidCookie();
        			requestContext.abortWith(ResponseMessageBuilder.authenticationRequired().cookie(cookie).message("Authentication failed.").build());
        		    return;
        		}
                 
                //Is user allowed?
                if(tokenSubject != null) {
					if(!isUserAllowed(tokenSubject.getRoleNames(), annotatedRoles))
					{
					    requestContext.abortWith(ResponseMessageBuilder.accessDenied().message("You do not have the appropriate permission to access this resource.").build());
					    return;
					}
				} else {
				    requestContext.abortWith(ResponseMessageBuilder.authenticationRequired().message("You must login to execute this operation.").build());
				    return;
                }
            }
        }
        
    }
    
	private TokenSubject authenticate(ContainerRequestContext requestContext) throws LoginException {
    	
        //Get request headers
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();

        // first try token
        final String token = headers.getFirst(AuthenticationServiceConstants.X_XSRF_TOKEN);
         
        if(StringUtil.isNotNull(token)) {
        	
        	TokenSubject tokenSubject = authenticationService.login(token);
        	return tokenSubject;
        }
                
        // Fetch authorization header
        final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

        if(authorization != null && !authorization.isEmpty()) {
            
            //Get encoded username and password
            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
             
            //Decode username and password
            String usernameAndPassword = null;
            try {
				usernameAndPassword = new String(Base64.decode(encodedUserPassword));
			} catch (IOException e) {
				throw new LoginException("Could not decode username and password. " + e.getMessage());
			}

            //Split username and password tokens
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();
             
            //Verifying Username and password
            logger.debug(username);
            logger.debug(password);
        	
            TokenSubject tokenSubject = authenticationService.login(username, password);
            return tokenSubject;
        } 
        
        return null;
    }
	
    private boolean isUserAllowed(Set<String> userRoles, Set<String> annotatedRoles) {
        if(!Collections.disjoint(userRoles, annotatedRoles)) {
            return true;
        } 
        return false;
	}
    
}