package com.prodyna.pac.rentawreck.backend.common.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * The Role entity.
 *
 * @author Oliver Teichmann
 *
 */
@Entity
@Table(name = "raw_roles")
@NamedQueries({ 
	@NamedQuery(name = Role.NQ_FIND_ALL, query = "SELECT x FROM Role x"),
	@NamedQuery(name = Role.NQ_FIND_ALL_COUNT, query = "SELECT COUNT(x) FROM Role x") 
})
public class Role extends AbstractEntity {

 	private static final long serialVersionUID = -4798391667549974653L;

	public static final String NQ_FIND_ALL = "Role.findAll";
	public static final String NQ_FIND_ALL_COUNT = "Role.findAllCount";

 	@NotNull
 	@Column(unique=true)
 	private String name;
 	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
