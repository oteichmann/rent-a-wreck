package com.prodyna.pac.rentawreck.backend.rentable.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
	@Enumerated(EnumType.STRING)
	@Column(name = "charter_status")
	private CharterStatus charterStatus;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "charter_start")
	private Date charterStart;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "charter_end")
	private Date charterEnd;

	@ManyToOne(optional = false)
	@JoinColumn(name = "aircraft")
	private Aircraft aircraft;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "pilot")
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
