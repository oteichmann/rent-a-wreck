/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity;

/**
 * License
 *
 * @author Oliver Teichmann
 *
 */
@Entity
@Table(name = "raw_licenses")
public class License extends AbstractEntity {

	private static final long serialVersionUID = 5103419272620661587L;

	@NotNull
	private AircraftType aircraftType;
	@NotNull
	private Date validTill;

	public AircraftType getAircraftType() {
		return aircraftType;
	}

	public void setAircraftType(AircraftType aircraftType) {
		this.aircraftType = aircraftType;
	}

	public Date getValidTill() {
		return validTill;
	}

	public void setValidTill(Date validTill) {
		this.validTill = validTill;
	}
	
}
