package com.prodyna.pac.rentawreck.backend.rentable.model;

/**
 * AircraftCharterStatus
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
