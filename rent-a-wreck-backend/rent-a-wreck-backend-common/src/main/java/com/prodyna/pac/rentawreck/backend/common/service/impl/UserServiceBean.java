package com.prodyna.pac.rentawreck.backend.common.service.impl;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import org.jboss.security.auth.spi.Util;

import com.prodyna.pac.rentawreck.backend.common.model.Role;
import com.prodyna.pac.rentawreck.backend.common.model.User;
import com.prodyna.pac.rentawreck.backend.common.monitoring.Monitored;
import com.prodyna.pac.rentawreck.backend.common.service.RoleService;
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
	
	@Inject
	private RoleService roleService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl. AbstractEntityPersistenceServiceBean
	 * #create(com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity)
	 */
	@Override
	public User create(String uuid, User user) {
		// Encrypt initial user password before persisting.
		encryptUserPassword(user);
		// User without role user does not work...
		Role userRole = roleService.findByName("user");
		user.getRoles().add(userRole);

		return super.create(uuid, user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.rentawreck.backend.common.service.impl. AbstractEntityPersistenceServiceBean
	 * #update(com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity)
	 */
	@Override
	public User update(String uuid, User user) {
		// Assure that update does not change the password. Special method must be used.
		User originalUser = em.find(User.class, user.getUuid());
		user.setPassword(originalUser.getPassword());
		return super.update(uuid, user);
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.UserService#updateUserPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public User updateUserPassword(String uuid, String password) {
		User user = em.find(User.class, uuid);
		user.setPassword(password);
		encryptUserPassword(user);

		return super.update(uuid, user);
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

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.service.UserService#findByUsername(java.lang.String)
	 */
	@Override
	public User findByUsername(String username) {
		TypedQuery<User> query = em.createQuery("SELECT x FROM User x WHERE x.username = :username", User.class);
		query.setParameter("username", username);
		return query.getSingleResult();
	}

}
