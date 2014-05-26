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
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.util.Base64;
import org.picketbox.util.StringUtil;

import com.prodyna.pac.rentawreck.backend.rest.model.TokenSubject;
import com.prodyna.pac.rentawreck.backend.rest.service.AuthenticationService;
import com.prodyna.pac.rentawreck.backend.rest.service.AuthenticationServiceConstants;
import com.prodyna.pac.rentawreck.backend.rest.util.AuthenticationUtil;
import com.prodyna.pac.rentawreck.backend.rest.util.ResponseMessageBuilder;

/**
 * This interceptor verify the access permissions for a user
 * based on username and passowrd provided in request
 * */ 
@Provider
public class SecurityInterceptor implements javax.ws.rs.container.ContainerRequestFilter {
	
	private static final Logger logger = Logger.getLogger(SecurityInterceptor.class);
	
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";

	@Inject
	private AuthenticationService authenticationService;
     
    @Override
    public void filter(ContainerRequestContext requestContext) {
    	
         ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();
        //Access allowed for all
        if( ! method.isAnnotationPresent(PermitAll.class))
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
                 
                //Is user allowed?
                try {
					if(!isUserAllowed(requestContext, annotatedRoles))
					{
					    requestContext.abortWith(ResponseMessageBuilder.accessDenied().message("You do not have the appropriate permission to access this resource.").build());
					    return;
					}
				} catch (LoginException e) {
			        requestContext.abortWith(ResponseMessageBuilder.authenticationRequired().message("Authentication failed.").build());
			        return;
				} catch (Exception e) {
					requestContext.abortWith(ResponseMessageBuilder.error().build());
	                return;
				}
            }
        }
        
    }
    
    private boolean isUserAllowed(ContainerRequestContext requestContext, Set<String> annotatedRoles) throws LoginException, IOException {
    	
    	// Check if request has been authenticated.
    	SecurityContext securityContext = requestContext.getSecurityContext();
    	String callerPrincipal = securityContext.getUserPrincipal() != null ? securityContext.getUserPrincipal().getName() : null;
    	
    	if(StringUtil.isNotNull(callerPrincipal)) {
    		for (String annotatedRole : annotatedRoles) {
				if(securityContext.isUserInRole(annotatedRole)) {
					return true;
				}
			}
    	}
    	
        Set<String> userRoles = getUserRoles(requestContext);
        return isUserAllowed(userRoles, annotatedRoles);
    	
	}
    
	private Set<String> getUserRoles(ContainerRequestContext requestContext) throws LoginException, IOException {
    	
        //Get request headers
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();
         
        //Fetch authorization header
        final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
        final String token = headers.getFirst(AuthenticationServiceConstants.X_XSRF_TOKEN);
         
        //If no authorization information present; block access
        if((authorization == null || authorization.isEmpty()) && StringUtil.isNullOrEmpty(token)) {
        	throw new LoginException();
        }
        
        //first try token
        if(StringUtil.isNotNull(token)) {
        	
        	TokenSubject tokenSubject = authenticationService.getTokenSubject(token);
        	return tokenSubject.getRoles();
        } else {
            
            //Get encoded username and password
            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
             
            //Decode username and password
            String usernameAndPassword = null;
            usernameAndPassword = new String(Base64.decode(encodedUserPassword));

            //Split username and password tokens
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();
             
            //Verifying Username and password
            logger.debug(username);
            logger.debug(password);
        	
        	Subject subject = AuthenticationUtil.authenticateUser(username, password);
        	return AuthenticationUtil.getRoles(subject);
        }
    }
	
    private boolean isUserAllowed(Set<String> userRoles, Set<String> annotatedRoles) {
        if(!Collections.disjoint(userRoles, annotatedRoles)) {
            return true;
        } 
        return false;
	}
    
}