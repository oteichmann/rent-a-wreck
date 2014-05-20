package com.prodyna.pac.rentawreck.backend.rest.util;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageBuilder {

    public static final String MESSAGE_PARAMETER = "message";
    public static final String TOKEN_PARAMETER = "token";

    private final ResponseBuilder response;
    private final Map<String, Object> messageData = new HashMap<String, Object>();

    public MessageBuilder(ResponseBuilder response) {
        this.response = response;
    }

    public static MessageBuilder badRequest() {
        return new MessageBuilder(Response.status(Response.Status.BAD_REQUEST));
    }

    public static MessageBuilder ok() {
        return new MessageBuilder(Response.ok());
    }

    public static MessageBuilder authenticationRequired() {
        return new MessageBuilder(Response.status(Response.Status.UNAUTHORIZED));
    }

    public static MessageBuilder accessDenied() {
        return new MessageBuilder(Response.status(Response.Status.FORBIDDEN));
    }
    
    public static MessageBuilder error() {
    	return new MessageBuilder(Response.status(Response.Status.INTERNAL_SERVER_ERROR));
    }

    @SuppressWarnings("unchecked")
    public MessageBuilder message(String... message) {
		List<String> actualMessages = (List<String>) this.messageData.get(MESSAGE_PARAMETER);

        if (actualMessages == null) {
            actualMessages = new ArrayList<String>();
            this.messageData.put(MESSAGE_PARAMETER, actualMessages);
        }

        actualMessages.addAll(Arrays.asList(message));

        return this;
    }

    public MessageBuilder token(String token) {
        this.messageData.put(TOKEN_PARAMETER, token);
        return this;
    }

    public Response build() {
        return this.response.entity(this.messageData).build();
    }
}
