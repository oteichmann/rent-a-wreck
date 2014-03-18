/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.model;

import java.util.List;

/**
 * @author oteichmann
 *
 */
public interface RentableService {
	
	public Rentable fetchById(long id);
	public List<Rentable> fetchAll();

}
