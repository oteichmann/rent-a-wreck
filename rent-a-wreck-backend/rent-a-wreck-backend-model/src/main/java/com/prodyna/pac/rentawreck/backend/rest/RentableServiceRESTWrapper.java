package com.prodyna.pac.rentawreck.backend.rest;
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


import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.rentawreck.backend.model.Rentable;
import com.prodyna.pac.rentawreck.backend.model.RentableService;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the
 * members table.
 */
@Path("/rentables")
@RequestScoped
public class RentableServiceRESTWrapper implements RentableService {

	@Inject @Named("rentableServiceBean")
	private RentableService service;

	@GET
	@Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
	public Rentable fetchById(@PathParam("id") long id) {
		return service.fetchById(id);
	}

	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Rentable> fetchAll() {
		return service.fetchAll();
	}
}
