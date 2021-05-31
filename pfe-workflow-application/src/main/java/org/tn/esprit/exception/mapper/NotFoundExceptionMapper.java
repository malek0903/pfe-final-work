package org.tn.esprit.exception.mapper;

import org.tn.esprit.error.Error;
import org.tn.esprit.error.ErrorCodes;
import org.tn.esprit.error.ErrorResponse;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException e) {

        ErrorResponse error = new ErrorResponse();
        error.getErrors().add(
            new Error(
                ErrorCodes.ERR_RESOURCE_NOT_FOUND,
                "Invalid Path",
                e.getMessage()
            )
        );
        return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    }
}
