/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@NamedQueries({ 
	@NamedQuery(name = Charter.NQ_FIND_ALL, query = "SELECT x FROM Charter x"),
	@NamedQuery(name = Charter.NQ_FIND_ALL_COUNT, query = "SELECT COUNT(x) FROM Charter x") 
})
public class Charter extends AbstractEntity {

	private static final long serialVersionUID = -36897664127654744L;
	
	public static final String NQ_FIND_ALL = "Charter.findAll";
	public static final String NQ_FIND_ALL_COUNT = "Charter.findAllCount";
	
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
