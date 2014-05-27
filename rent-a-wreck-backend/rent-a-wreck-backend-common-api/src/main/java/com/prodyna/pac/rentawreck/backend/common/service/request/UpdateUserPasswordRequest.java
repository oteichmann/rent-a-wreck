package com.prodyna.pac.rentawreck.backend.common.service.request;

import java.io.Serializable;

public class UpdateUserPasswordRequest implements Serializable {

	private static final long serialVersionUID = 9062746946434683496L;
	
	private String uuid;
	private String password;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
