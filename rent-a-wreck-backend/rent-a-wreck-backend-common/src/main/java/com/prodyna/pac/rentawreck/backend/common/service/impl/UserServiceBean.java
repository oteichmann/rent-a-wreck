package com.prodyna.pac.rentawreck.backend.common.service.impl;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.security.auth.spi.Util;

import com.prodyna.pac.rentawreck.backend.common.model.User;
import com.prodyna.pac.rentawreck.backend.common.service.UserService;
import com.prodyna.pac.rentawreck.backend.common.service.request.UpdateUserPasswordRequest;

@Stateless
public class UserServiceBean extends EntityPersistenceServiceBean<User> implements
		UserService {
	
	private static final String REALM = "rent-a-wreck";
	
	@Inject
	private Logger log;
	
	@Override
	public User create(User user) {
		// Encrypt initial user password before persisting.
		encryptUserPassword(user);
		
		return super.create(user);
	}
	
	@Override
	public User update(User user) {
		// Assure that update does not change the password. Special method must be used.
		User originalUser = em.find(User.class, user.getUuid());
		user.setPassword(originalUser.getPassword());
		return super.update(user);
	}
	
	@Override
	public User updateUserPassword(
			UpdateUserPasswordRequest updateUserPasswordRequest) {
		User user = em.find(User.class, updateUserPasswordRequest.getUuid());
		user.setPassword(updateUserPasswordRequest.getPassword());
		encryptUserPassword(user);
		
		return super.update(user);
	}
	
	private void encryptUserPassword(User user) {
        String A1 = user.getUsername() + ":" + REALM + ":" + user.getPassword();
		String enryptedPassword = Util.createPasswordHash("MD5",
                Util.RFC2617_ENCODING, null, null, A1);
		
		user.setPassword(enryptedPassword);
	}

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

	@Override
	protected Logger getLooger() {
		return log;
	}



}
