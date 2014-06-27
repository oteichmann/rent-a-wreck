package com.prodyna.pac.rentawreck.backend.rentable.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
	@Temporal(TemporalType.DATE)
	private Date charterStart;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date charterEnd;

	@OneToOne(optional = false)
	private Aircraft aircraft;
	
	@OneToOne(optional = false)
	private Pilot pilot;

	public Aircraft getAircraft() {
		return aircraft;
	}

	public void setAircraft(Aircraft aircraft) {
		this.aircraft = aircraft;
	}

	public Pilot getPilot() {
		return pilot;
	}

	public void setPilot(Pilot pilot) {
		this.pilot = pilot;
	}

	public CharterStatus getCharterStatus() {
		return charterStatus;
	}

	public void setCharterStatus(CharterStatus charterStatus) {
		this.charterStatus = charterStatus;
	}

	public Date getCharterStart() {
		return charterStart;
	}

	public void setCharterStart(Date charterStart) {
		this.charterStart = charterStart;
	}

	public Date getCharterEnd() {
		return charterEnd;
	}

	public void setCharterEnd(Date charterEnd) {
		this.charterEnd = charterEnd;
	}
	
	

}
