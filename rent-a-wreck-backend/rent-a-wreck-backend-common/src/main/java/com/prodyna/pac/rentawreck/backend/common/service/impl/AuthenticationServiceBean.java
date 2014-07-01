package com.prodyna.pac.rentawreck.backend.common.service.impl;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.jboss.security.SecurityContext;
import org.jboss.security.SecurityContextAssociation;
import org.jboss.security.SecurityContextFactory;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.callback.JBossCallbackHandler;
import org.jboss.security.auth.callback.UsernamePasswordHandler;
import org.picketbox.util.StringUtil;

import com.prodyna.pac.rentawreck.backend.common.model.TokenSubject;
import com.prodyna.pac.rentawreck.backend.common.model.User;
import com.prodyna.pac.rentawreck.backend.common.service.AuthenticationService;
import com.prodyna.pac.rentawreck.backend.common.service.UserService;

@ApplicationScoped
public class AuthenticationServiceBean implements AuthenticationService {
	
	private static final String SECURITY_DOMAIN = "rent-a-wreck";
	
	@Inject
	private UserService userService;
	
	private Map<String, TokenSubject> tokenSubjectCache = new HashMap<String, TokenSubject>();

	@Override
	public TokenSubject login(String username, String password) throws LoginException {
		
		if(StringUtil.isNullOrEmpty(username) && StringUtil.isNullOrEmpty(password)) {
			throw new LoginException("Username and Password may not be null or empty.");
		}
		
        authenticateUser(username, password);
        
        User user = null;
        try {
        	user = userService.findByUsername(username);
        	
        	String token = UUID.randomUUID().toString();
        	
        	TokenSubject tokenSubject = new TokenSubject(token, user, password);
        	tokenSubjectCache.put(token, tokenSubject);
        	
        	return tokenSubject;
        } catch(javax.persistence.PersistenceException e) {
        	throw new LoginException("Could not lookup authenticated user in database.");
        }
	}

	@Override
	public TokenSubject login(String token) throws LoginException {
		
		if(StringUtil.isNullOrEmpty(token)) {
			throw new LoginException("Token may not be null or empty.");
		}
		
		if(tokenSubjectCache.containsKey(token)) {
			TokenSubject tokenSubject = tokenSubjectCache.get(token);
			authenticateUser(tokenSubject.getUsername(), tokenSubject.getPassword());
			return tokenSubject;
		} else {
			 throw new LoginException("Token not valid!");
		}
	}

	@Override
	public boolean logout(String token) {

		if(tokenSubjectCache.containsKey(token)) {
			tokenSubjectCache.remove(token);
			return true;
		}
		return false;
	}
	
	private Subject authenticateUser(String username, Object password) throws LoginException {
		
        JBossCallbackHandler handler = new JBossCallbackHandler();
        Principal principal = new SimplePrincipal(username);
        handler.setSecurityInfo(principal, password);
        LoginContext lc = new LoginContext(SECURITY_DOMAIN, handler);
		
		lc.login();
			
		Subject subject = lc.getSubject();
		
		
//        SecurityContext originalSecurityContext = SecurityContextAssociation.getSecurityContext();  
        try {  
            // Bind principal/subject to the current thread  
            SecurityContext sc = SecurityContextFactory.createSecurityContext(SECURITY_DOMAIN);  
            sc.getUtil().createSubjectInfo(subject.getPrincipals().iterator().next(), password, subject);  
            SecurityContextAssociation.setSecurityContext(sc);  
  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        } finally {  
//            SecurityContextAssociation.setSecurityContext(originalSecurityContext);  
        }  
		
		return subject;
	}
	
//	@Override
//	public TokenSubject getTokenSubject(String token) {
//		return tokenSubjectCache.get(token);
//	}
}
	
