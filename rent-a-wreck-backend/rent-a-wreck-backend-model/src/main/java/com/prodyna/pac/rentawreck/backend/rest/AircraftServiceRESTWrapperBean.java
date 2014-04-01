/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.prodyna.pac.rentawreck.backend.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.rentawreck.backend.model.Aircraft;
import com.prodyna.pac.rentawreck.backend.model.AircraftService;

/**
 * @author oteichmann
 */
@Path("/aircrafts")
@RequestScoped
public class AircraftServiceRESTWrapperBean {

	@Inject @Named("aircraftServiceBean")
	private AircraftService service;

	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Aircraft create(Aircraft aircraft) {
		return service.create(aircraft);
	}

	@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Aircraft update(Aircraft aircraft) {
		return service.update(aircraft);
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void delete(@PathParam("id") long id) {
		service.delete(id);
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
	public Aircraft findById(@PathParam("id") long id) {
		return service.findById(id);
	}

	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Aircraft> findAll() {
		return service.findAll();
	}
	
	@GET
	@Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
	public int findAllCount() {
		return service.findAllCount();
	}
}
