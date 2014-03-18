package com.prodyna.pac.rentawreck.backend.model;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Named
@Stateless
@NamedQueries({
	@NamedQuery(name = "Aircraft.findAll", query = "SELECT a FROM Aircraft a"),
	@NamedQuery(name = "Aircraft.findAllCount", query = "SELECT COUNT(a) FROM Aircraft a")
})
public class AircraftServiceBean implements AircraftService {

	@PersistenceContext
	private EntityManager em;

	// @Inject @Named("log")
	// private Logger log;

	/**
	 * Default constructor.
	 */
	public AircraftServiceBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Aircraft create(Aircraft aircraft) {
		em.persist(aircraft);
		return aircraft;
	}

	@Override
	public Aircraft update(Aircraft aircraft) {
		aircraft = em.merge(aircraft);
		return aircraft;
	}

	@Override
	public void delete(long id) {
		Aircraft aircraft = findById(id);
		em.remove(aircraft);
	}

	@Override
	public Aircraft findById(long id) {
		Aircraft aircraft = em.find(Aircraft.class, id);
		return aircraft;
	}

	@Override
	public List<Aircraft> findAll() {
//		TypedQuery<Aircraft> query = em.createNamedQuery("Aircraft.findAll", Aircraft.class);
		TypedQuery<Aircraft> query = em.createQuery("SELECT a FROM Aircraft a", Aircraft.class);
		List<Aircraft> results = query.getResultList();

		return Collections.unmodifiableList(results);
	}
	
	@Override
	public int findAllCount() {
//		int count = ((Number)em.createNamedQuery("Aircraft.findAllCount").getSingleResult()).intValue();
		int count = ((Number)em.createQuery("SELECT COUNT(a) FROM Aircraft a").getSingleResult()).intValue();
		return count;
	}

}
