package com.prodyna.pac.rentawreck.backend.rest;
 
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.security.acl.Group;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.util.Base64;
import org.jboss.security.SecurityContextAssociation;
import org.jboss.security.SecurityContextFactory;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.callback.JBossCallbackHandler;
 
/**
 * This interceptor verify the access permissions for a user
 * based on username and passowrd provided in request
 * */ 
@Provider
public class SecurityInterceptor implements javax.ws.rs.container.ContainerRequestFilter {
	
	private static final Logger logger = Logger.getLogger(SecurityInterceptor.class);
	
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401, new Headers<Object>());;
    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());;
    private static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR", 500, new Headers<Object>());

	private static final String X_XSRF_TOKEN = "X-XSRF-TOKEN";
     
    @Override
    public void filter(ContainerRequestContext requestContext) {
    	
    	SecurityContext securityContext = requestContext.getSecurityContext();
    	String callerPrincipal = securityContext.getUserPrincipal() != null ? securityContext.getUserPrincipal().getName() : null;
    	securityContext.isUserInRole("admin");
    	securityContext.isUserInRole("user");

        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();
        //Access allowed for all
        if( ! method.isAnnotationPresent(PermitAll.class))
        {
            //Access denied for all
            if(method.isAnnotationPresent(DenyAll.class))
            {
                requestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }
             
            //Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
             
            //Fetch authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
            final String token = headers.getFirst(X_XSRF_TOKEN);
             
            //If no authorization information present; block access
            if(authorization == null || authorization.isEmpty())
            {
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }
             
            //Get encoded username and password
            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
             
            //Decode username and password
            String usernameAndPassword = null;
            try {
                usernameAndPassword = new String(Base64.decode(encodedUserPassword));
            } catch (IOException e) {
                requestContext.abortWith(SERVER_ERROR);
                return;
            }
 
            //Split username and password tokens
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();
             
            //Verifying Username and password
            logger.debug(username);
            logger.debug(password);
             
            //Verify user access
            if(method.isAnnotationPresent(RolesAllowed.class))
            {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> annotatedRoles = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
                 
                //Is user valid?
                try {
					if( ! isUserAllowed(username, password, annotatedRoles))
					{
					    requestContext.abortWith(ACCESS_DENIED);
					    return;
					}
				} catch (Exception e) {
					requestContext.abortWith(SERVER_ERROR);
	                return;
				}
            }
        }
        
    }
    private boolean isUserAllowed(final String username, final String password, final Set<String> annotatedRoles) throws Exception
    {
        boolean isAllowed = false;
        
        /* Access the security domain to which the security manager is bound. This is
        the xyz component of java:/jaas/xyz name used when defining the security-domain
        or role-mapping-manager config elements. */
        String name = "rent-a-wreck";
        JBossCallbackHandler handler = new JBossCallbackHandler();
        Principal principal = new SimplePrincipal(username);
        handler.setSecurityInfo(principal, password);
        LoginContext lc = new LoginContext(name, handler);
        // Validate principal, credential using the LoginModules configured for 'name'
        lc.login();
        Subject subject = lc.getSubject();
        Set<Group> subjectGroups = subject.getPrincipals(Group.class);
        // Get the Group whose name is 'Roles'
        Group rolesGroup = getGroup(subjectGroups, "Roles");
        Set<String> userRoles = getGroupMembers(rolesGroup);
         
        //Step 1. Fetch password from database and match with password in argument
        //If both match then get the defined role for user from database and continue; else return isAllowed [false]
        //Access the database and do this part yourself
        //String userRole = userMgr.getUserRole(username);
        //String userRole = "admin";
         
        //Step 2. Verify user role
        if(!Collections.disjoint(userRoles, annotatedRoles))
        {
            isAllowed = true;
        }
        return isAllowed;
    }
    
	private Set<String> getGroupMembers(Group group) {
		Set<String> set = new HashSet<String>();
		Enumeration<? extends Principal> members = group.members();
		while (members.hasMoreElements()) {
			Principal type = (Principal) members.nextElement();
			set.add(type.getName());
		}
		return set;
	}
	private Group getGroup(Set<Group> subjectGroups, String string) {
		Group group = null;
        for (Group subjectGroup : subjectGroups) {
			if(subjectGroup.getName().equals("Roles")) {
				group = subjectGroup;
			}
		}
		return group;
	}
     
}