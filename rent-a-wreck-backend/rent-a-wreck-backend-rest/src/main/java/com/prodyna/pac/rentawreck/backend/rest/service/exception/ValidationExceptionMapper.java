package com.prodyna.pac.rentawreck.backend.rest.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.prodyna.pac.rentawreck.backend.common.service.exception.ValidationException;

/**
 * ValidationExceptionMapper
 *
 * @author Oliver Teichmann
 *
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException exception)
    {
        return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build(); 
    }
}