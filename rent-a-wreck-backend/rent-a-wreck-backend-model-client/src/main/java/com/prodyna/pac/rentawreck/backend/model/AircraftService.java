/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.model;

import java.util.List;

/**
 * @author oteichmann
 *
 */
public interface AircraftService {
	
	public Aircraft create(Aircraft aircraft);
	public Aircraft update(Aircraft aircraft);
	public void delete(long id);
	public Aircraft findById(long id);
	public List<Aircraft> findAll();
	public int findAllCount();

}
