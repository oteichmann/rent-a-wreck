package com.prodyna.pac.rentawreck.backend.rest.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * GenericExceptionMapper
 *
 * @author Oliver Teichmann
 *
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception)
    {
        return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build(); 
    }
}