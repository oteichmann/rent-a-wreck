/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity;

/**
 * Pilot
 *
 * @author Oliver Teichmann
 *
 */
@Entity
@Table(name = "raw_pilots")
public class Pilot extends AbstractEntity {
	
	private static final long serialVersionUID = 4892991884961881279L;

	@NotNull
	private String userUuid;
	
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @ElementCollection(targetClass=License.class)
    @Column(name = "LICENSE_UUID")
	private Set<License> licenseSet;

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public Set<License> getLicenseSet() {
		return licenseSet;
	}

	public void setLicenseSet(Set<License> licenseSet) {
		this.licenseSet = licenseSet;
	}

}
