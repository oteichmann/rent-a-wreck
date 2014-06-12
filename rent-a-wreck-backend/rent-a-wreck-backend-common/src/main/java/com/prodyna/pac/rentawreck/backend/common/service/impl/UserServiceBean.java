package com.prodyna.pac.rentawreck.backend.common.service.impl;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.jboss.security.auth.spi.Util;

import com.prodyna.pac.rentawreck.backend.common.model.User;
import com.prodyna.pac.rentawreck.backend.common.monitoring.Monitored;
import com.prodyna.pac.rentawreck.backend.common.service.UserService;

/**
 * Implementation of the {@link UserService} interface.
 * 
 * @author Oliver Teichmann
 * 
 */
@Stateless
@Monitored
public class UserServiceBean extends AbstractEntityPersistenceServiceBean<User> implements UserService {

	@Inject
	private Logger log;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl. AbstractEntityPersistenceServiceBean
	 * #create(com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity)
	 */
	@Override
	public User create(User user) {
		// Encrypt initial user password before persisting.
		encryptUserPassword(user);

		return super.create(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl. AbstractEntityPersistenceServiceBean
	 * #update(com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity)
	 */
	@Override
	public User update(User user) {
		// Assure that update does not change the password. Special method must
		// be used.
		User originalUser = em.find(User.class, user.getUuid());
		user.setPassword(originalUser.getPassword());
		return super.update(user);
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.UserService#updateUserPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public User updateUserPassword(String uuid, String password) {
		User user = em.find(User.class, uuid);
		user.setPassword(password);
		encryptUserPassword(user);

		return super.update(user);
	}

	private void encryptUserPassword(User user) {
		// String A1 = user.getUsername() + ":" + "rent-a-wreck" + ":" +
		// user.getPassword();
		String enryptedPassword = Util.createPasswordHash("MD5", Util.RFC2617_ENCODING, null, null, user.getPassword());

		user.setPassword(enryptedPassword);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl. AbstractEntityPersistenceServiceBean#getEntityClass()
	 */
	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl. AbstractEntityPersistenceServiceBean#getLooger()
	 */
	@Override
	protected Logger getLooger() {
		return log;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#getFindAllNamedQuery()
	 */
	@Override
	protected String getFindAllNamedQuery() {
		return User.NQ_FIND_ALL;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl.AbstractEntityPersistenceServiceBean#getFindAllCountNamedQuery()
	 */
	@Override
	protected String getFindAllCountNamedQuery() {
		return User.NQ_FIND_ALL_COUNT;
	}

}
