package com.prodyna.pac.rentawreck.backend.rentable.model;

/**
 * Wrapper object to display the status of an aircrafts. It contains the aircraft the currently active charter and 
 * boolean if the aircraft is currently available or not.
 *
 * @author Oliver Teichmann
 *
 */
public class AircraftCharterStatus {
	
	private Aircraft aircraft;
	private Charter charter;
	private Boolean available;
	
	public Aircraft getAircraft() {
		return aircraft;
	}
	public void setAircraft(Aircraft aircraft) {
		this.aircraft = aircraft;
	}
	public Charter getCharter() {
		return charter;
	}
	public void setCharter(Charter charter) {
		this.charter = charter;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}

}
