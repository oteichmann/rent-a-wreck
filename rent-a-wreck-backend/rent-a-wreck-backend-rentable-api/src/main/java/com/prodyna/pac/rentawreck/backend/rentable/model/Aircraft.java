package com.prodyna.pac.rentawreck.backend.rentable.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity;

/**
 * Aircraft 
 * 
 * @author oteichmann
 * 
 */
@Entity
@Table(name = "raw_aircrafts")
public class Aircraft extends AbstractEntity {

	private static final long serialVersionUID = -6523878611712737965L;

	@NotNull
	private String id;
	@NotNull
	private AircraftType type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AircraftType getType() {
		return type;
	}

	public void setType(AircraftType type) {
		this.type = type;
	}

}
