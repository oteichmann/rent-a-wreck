package com.prodyna.pac.rentawreck.backend.model;


import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class RentableServiceBean implements RentableService {

//	@Inject
//	private Logger log;

	/**
	 * Default constructor.
	 */
	public RentableServiceBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Rentable fetchById(long id) {
		// TODO Auto-generated method stub
//		log.severe("Not implemented yet!");
		return null;
	}

	@Override
	public List<Rentable> fetchAll() {
		// TODO Auto-generated method stub
//		log.severe("Not implemented yet!");
		return null;
	}

}
