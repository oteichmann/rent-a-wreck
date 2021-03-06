package com.prodyna.pac.rentawreck.backend.rentable.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity;
import com.prodyna.pac.rentawreck.backend.common.model.User;

/**
 * Pilot
 *
 * @author Oliver Teichmann
 *
 */
@Entity
@Table(name = "raw_pilots")
@NamedQueries({ 
	@NamedQuery(name = Pilot.NQ_FIND_ALL, query = "SELECT x FROM Pilot x"),
	@NamedQuery(name = Pilot.NQ_FIND_ALL_COUNT, query = "SELECT COUNT(x) FROM Pilot x") 
})
public class Pilot extends AbstractEntity {
	
	private static final long serialVersionUID = 4892991884961881279L;
	
	public static final String NQ_FIND_ALL = "Pilot.findAll";
	public static final String NQ_FIND_ALL_COUNT = "Pilot.findAllCount";

	@NotNull
	@OneToOne
	@JoinColumn(name="user_uuid", unique=true)
	private User user;
	
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="pilot_uuid")
	private Set<License> licenses = new HashSet<License>();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<License> getLicenses() {
		return licenses;
	}

	public void setLicenses(Set<License> licenses) {
		this.licenses = licenses;
	}

}
