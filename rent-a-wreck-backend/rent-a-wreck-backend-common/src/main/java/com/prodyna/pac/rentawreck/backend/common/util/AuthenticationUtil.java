package com.prodyna.pac.rentawreck.backend.common.util;

import java.security.Principal;
import java.security.acl.Group;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.callback.JBossCallbackHandler;

import com.prodyna.pac.rentawreck.backend.common.service.AuthenticationService;

/**
 * Utility class that provides different authentication related methods. 
 *
 * @author Oliver Teichmann
 *
 * @deprecated Handled by {@link AuthenticationService} implementation.
 */
@Deprecated()
public class AuthenticationUtil {
	
	private static final String SECURITY_DOMAIN = "rent-a-wreck";
	
	/**
	 * Authenticate a user against the application security domain
	 * 
	 * @param username
	 * @param password
	 * @return The authenticated subject.
	 * @throws LoginException
	 */
	public static Subject authenticateUser(String username, Object password) throws LoginException {
		
        JBossCallbackHandler handler = new JBossCallbackHandler();
        Principal principal = new SimplePrincipal(username);
        handler.setSecurityInfo(principal, password);
        LoginContext lc;
		lc = new LoginContext(SECURITY_DOMAIN, handler);
		
		// Validate principal, credential using the LoginModules configured for 'name'
		lc.login();
			
		Subject subject = lc.getSubject();
		
		return subject;
	}
	
	/**
	 * Returns the roles of a given subject as Set of Strings.
	 * @param subject
	 * @return The roles of the subject.
	 */
	public static Set<String> getRoles(Subject subject) {
		
		Set<Group> subjectGroups = subject.getPrincipals(Group.class);
        // Get the Group whose name is 'Roles'
        Group rolesGroup = getGroup(subjectGroups, "Roles");
        Set<String> userRoles = getGroupMembers(rolesGroup);
        
        return userRoles;
	}

	private static Group getGroup(Set<Group> subjectGroups, String string) {
		
		Group group = null;
		
		if (subjectGroups != null) {
	        for (Group subjectGroup : subjectGroups) {
				if(subjectGroup.getName().equals("Roles")) {
					group = subjectGroup;
					break;
				}
			}
		}
		
		return group;
	}
	
	private static Set<String> getGroupMembers(Group group){
		
		Set<String> set = new HashSet<String>();
		
		if (group != null) {
			Enumeration<? extends Principal> members = group.members();
			while (members.hasMoreElements()) {
				Principal type = (Principal) members.nextElement();
				set.add(type.getName());
			}
		}
		
		return set;
	}

}
