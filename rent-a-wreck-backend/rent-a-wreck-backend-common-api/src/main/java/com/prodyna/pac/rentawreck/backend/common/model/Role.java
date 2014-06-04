package com.prodyna.pac.rentawreck.backend.common.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * The Role entity.
 *
 * @author Oliver Teichmann
 *
 */
@Entity
@Table(name = "raw_roles")
public class Role extends AbstractEntity {

 	private static final long serialVersionUID = -4798391667549974653L;
	
 	@NotNull
 	private String username;
 	@NotNull
 	@ElementCollection  
    protected Set<String> roles = new HashSet<String>();
 	@NotNull
 	private String roleGroup;
 	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	public String getRoleGroup() {
		return roleGroup;
	}
	public void setRoleGroup(String roleGroup) {
		this.roleGroup = roleGroup;
	}
	
}
