package org.tn.esprit.exception.mapper;

import org.tn.esprit.error.Error;
import org.tn.esprit.error.ErrorCodes;
import org.tn.esprit.error.ErrorResponse;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        ErrorResponse error = new ErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getErrors().add(
                new Error(
                    ErrorCodes.ERR_CONSTRAINT_CHECK_FAILED,
                    violation.getPropertyPath().toString(),
                    violation.getMessage())
            );
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
}
