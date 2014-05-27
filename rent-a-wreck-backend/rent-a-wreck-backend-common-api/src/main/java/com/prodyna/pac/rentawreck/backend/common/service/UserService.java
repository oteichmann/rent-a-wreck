package com.prodyna.pac.rentawreck.backend.common.service;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.rentawreck.backend.common.model.User;
import com.prodyna.pac.rentawreck.backend.common.service.request.UpdateUserPasswordRequest;

@Path("/users/")
public interface UserService extends EntityPersistenceService<User> {

	@POST
	@Path("/update/password/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("admin")
	User updateUserPassword(UpdateUserPasswordRequest updateUserPasswordRequest);
}
