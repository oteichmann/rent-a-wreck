package com.prodyna.pac.rentawreck.backend.rentable.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;

import com.prodyna.pac.rentawreck.backend.rentable.model.Aircraft;
import com.prodyna.pac.rentawreck.backend.rentable.service.AircraftService;

@Stateless
@NamedQueries({ @NamedQuery(name = "Aircraft.findAll", query = "SELECT a FROM Aircraft a"),
		@NamedQuery(name = "Aircraft.findAllCount", query = "SELECT COUNT(a) FROM Aircraft a") })
public class AircraftServiceBean implements AircraftService {

	@Inject
	private EntityManager em;

	@Inject
	private Logger log;

	@Override
	public Aircraft create(Aircraft aircraft) {
		if (log.isLoggable(Level.FINE)) {
			log.fine("Creating a new aircraft");
		}
		em.persist(aircraft);
		return aircraft;
	}
	
	@Override
	public Aircraft findById(String uuid) {
		Aircraft aircraft = em.find(Aircraft.class, uuid);
		return aircraft;
	}

	@Override
	public Aircraft update(Aircraft aircraft) {
		aircraft = em.merge(aircraft);
		return aircraft;
	}

	@Override
	public void delete(String uuid) {
		Aircraft aircraft = findById(uuid);
		em.remove(aircraft);
	}

	@Override
	public List<Aircraft> findAll() {
		if (log.isLoggable(Level.FINE)) {
			log.fine("Creating a new aircraft");
		}
		// TypedQuery<Aircraft> query = em.createNamedQuery("Aircraft.findAll",
		// Aircraft.class);
		TypedQuery<Aircraft> query = em.createQuery("SELECT a FROM Aircraft a", Aircraft.class);
		List<Aircraft> results = query.getResultList();

		return Collections.unmodifiableList(results);
	}

	@Override
	public int findAllCount() {
		// int count =
		// ((Number)em.createNamedQuery("Aircraft.findAllCount").getSingleResult()).intValue();
		int count = ((Number) em.createQuery("SELECT COUNT(a) FROM Aircraft a").getSingleResult()).intValue();
		return count;
	}

}
