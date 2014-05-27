package com.prodyna.pac.rentawreck.backend.common.service.impl;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.pac.rentawreck.backend.common.model.User;
import com.prodyna.pac.rentawreck.backend.common.service.UserService;

@Stateless
public class UserServiceBean extends EntityPersistenceServiceBean<User> implements
		UserService {
	
	@Inject
	private Logger log;
	
	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

	@Override
	protected Logger getLooger() {
		return log;
	}

}
