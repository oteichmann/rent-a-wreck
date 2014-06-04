package com.prodyna.pac.rentawreck.backend.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class User extends AbstractEntity {

	private static final long serialVersionUID = -5973878597842495317L;

	@NotNull
 	@Column(unique=true)
 	private String username;
 	@NotNull
 	private String password;
 	// TODO: add email validation pattern
 	private String email;
 	private String firstName;
 	private String lastName;
 	
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
	
}
