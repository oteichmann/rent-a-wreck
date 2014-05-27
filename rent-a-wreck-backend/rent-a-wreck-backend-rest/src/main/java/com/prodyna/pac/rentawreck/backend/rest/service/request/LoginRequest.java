package com.prodyna.pac.rentawreck.backend.rest.service.request;

import java.io.Serializable;

public class LoginRequest implements Serializable {

	private static final long serialVersionUID = -1163838278679161405L;
	
	private String username;
    private String password;

    /**
     * @return the userId
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param userId the userId to set
     */
    public void setUsername(String userId) {
        this.username = userId;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
