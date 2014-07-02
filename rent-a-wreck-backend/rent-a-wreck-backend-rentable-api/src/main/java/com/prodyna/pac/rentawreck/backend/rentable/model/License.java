package com.prodyna.pac.rentawreck.backend.rentable.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity;
import com.prodyna.pac.rentawreck.backend.common.service.rest.DateFormat;

/**
 * License
 *
 * @author Oliver Teichmann
 *
 */
@Entity
@Table(name = "raw_licenses")
@NamedQueries({ 
	@NamedQuery(name = License.NQ_FIND_ALL, query = "SELECT x FROM License x"),
	@NamedQuery(name = License.NQ_FIND_ALL_COUNT, query = "SELECT COUNT(x) FROM License x") 
})
public class License extends AbstractEntity {

	private static final long serialVersionUID = 5103419272620661587L;
	
	public static final String NQ_FIND_ALL = "License.findAll";
	public static final String NQ_FIND_ALL_COUNT = "License.findAllCount";

	@NotNull
	private AircraftType aircraftType;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateFormat("yyyy-MM-dd")
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
