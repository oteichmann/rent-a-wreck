package com.prodyna.pac.rentawreck.backend.rest.util;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseMessageBuilder {

    public static final String MESSAGE_PARAMETER = "message";
    public static final String TOKEN_PARAMETER = "token";

    private final ResponseBuilder response;
    private final Map<String, Object> messageData = new HashMap<String, Object>();

    public ResponseMessageBuilder(ResponseBuilder response) {
        this.response = response;
    }

    public static ResponseMessageBuilder badRequest() {
        return new ResponseMessageBuilder(Response.status(Response.Status.BAD_REQUEST));
    }

    public static ResponseMessageBuilder ok() {
        return new ResponseMessageBuilder(Response.ok());
    }

    public static ResponseMessageBuilder authenticationRequired() {
        return new ResponseMessageBuilder(Response.status(Response.Status.UNAUTHORIZED));
    }

    public static ResponseMessageBuilder accessDenied() {
        return new ResponseMessageBuilder(Response.status(Response.Status.FORBIDDEN));
    }
    
    public static ResponseMessageBuilder error() {
    	return new ResponseMessageBuilder(Response.status(Response.Status.INTERNAL_SERVER_ERROR));
    }
    
    @SuppressWarnings("unchecked")
    public ResponseMessageBuilder message(String... message) {
		List<String> actualMessages = (List<String>) this.messageData.get(MESSAGE_PARAMETER);

        if (actualMessages == null) {
            actualMessages = new ArrayList<String>();
            this.messageData.put(MESSAGE_PARAMETER, actualMessages);
        }

        actualMessages.addAll(Arrays.asList(message));

        return this;
    }

    public ResponseMessageBuilder cookie(NewCookie cookie){
    	this.response.cookie(cookie);
    	return this;
    }
    
    public Response build() {
        return this.response.entity(this.messageData).build();
    }
}
