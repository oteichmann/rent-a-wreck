package com.prodyna.pac.rentawreck.backend.rest.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * GenericRuntimeExceptionMapper
 *
 * @author Oliver Teichmann
 *
 */
@Provider
public class GenericRuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception)
    {
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build(); 
    }
}