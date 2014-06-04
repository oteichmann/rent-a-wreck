/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity;

/**
 * Charter
 *
 * @author Oliver Teichmann
 *
 */
@Entity
@Table(name = "raw_charters")
public class Charter extends AbstractEntity {

	private static final long serialVersionUID = -36897664127654744L;
	
	@NotNull
	private CharterStatus charterStatus;
	@NotNull
	private String aircraftUuid;
	@NotNull
	private String pilotUuid;

	public CharterStatus getCharterStatus() {
		return charterStatus;
	}

	public void setCharterStatus(CharterStatus charterStatus) {
		this.charterStatus = charterStatus;
	}

	public String getAircraftUuid() {
		return aircraftUuid;
	}

	public void setAircraftUuid(String aircraftUuid) {
		this.aircraftUuid = aircraftUuid;
	}

	public String getPilotUuid() {
		return pilotUuid;
	}

	public void setPilotUuid(String pilotUuid) {
		this.pilotUuid = pilotUuid;
	}
	
}
