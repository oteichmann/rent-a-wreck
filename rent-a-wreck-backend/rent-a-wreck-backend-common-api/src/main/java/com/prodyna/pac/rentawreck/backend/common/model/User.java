package com.prodyna.pac.rentawreck.backend.common.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;


/**
 * The User entity.
 *
 * @author Oliver Teichmann
 *
 */
@Entity
@Table(name = "raw_users", uniqueConstraints=@UniqueConstraint(columnNames="USERNAME"))
@NamedQueries({ 
	@NamedQuery(name = User.NQ_FIND_ALL, query = "SELECT x FROM User x"),
	@NamedQuery(name = User.NQ_FIND_ALL_COUNT, query = "SELECT COUNT(x) FROM User x") 
})
public class User extends AbstractEntity {

	private static final long serialVersionUID = -5973878597842495317L;
	
	public static final String NQ_FIND_ALL = "User.findAll";
	public static final String NQ_FIND_ALL_COUNT = "User.findAllCount";

	@NotNull
 	@Column(unique=true)
 	private String username;
 	@NotNull
 	private String password;
 	// TODO: add email validation pattern
 	private String email;
 	@Column(name="first_name")
 	private String firstName;
 	@Column(name="last_name")
 	private String lastName;
 	
	@NotNull
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "raw_users_roles",
               joinColumns = @JoinColumn(name = "user_uuid"),
               inverseJoinColumns = @JoinColumn(name = "role_uuid"))
 	private List<Role> roles = new ArrayList<Role>();
 	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
