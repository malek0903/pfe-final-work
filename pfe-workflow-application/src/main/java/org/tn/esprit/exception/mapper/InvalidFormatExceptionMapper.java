package org.tn.esprit.exception.mapper;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.tn.esprit.error.Error;
import org.tn.esprit.error.ErrorCodes;
import org.tn.esprit.error.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidFormatExceptionMapper implements ExceptionMapper<InvalidFormatException> {

    @Override
    public Response toResponse(InvalidFormatException e) {
        ErrorResponse error = new ErrorResponse();

        error.getErrors().add(
            new Error(
                ErrorCodes.ERR_REQUEST_PARAMS_BODY_VALIDATION_FAILED,
                "Invalid request format. Please verify your request body and try again !!",
                e.getMessage()
            )
        );
        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
}
