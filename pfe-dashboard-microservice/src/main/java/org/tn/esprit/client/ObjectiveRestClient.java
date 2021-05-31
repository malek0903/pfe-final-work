package org.tn.esprit.client;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.tn.esprit.commons.dto.ObjectiveDto;
import org.tn.esprit.commons.dto.ReviewDto;
import org.tn.esprit.commons.dto.WorkflowDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RegisterRestClient
@Path("")
@CircuitBreaker(requestVolumeThreshold = 10, delay = 15000)
@Retry(maxRetries = 4)
@Timeout(500)
@Produces(MediaType.APPLICATION_JSON)
public interface ObjectiveRestClient {

    @GET
    @Path("/reviews")
    List<ReviewDto> getAllReviews();

    @GET
    @Path("/reviews/{workflowId}")
    boolean existById(@PathParam(value = "workflowId") Long workflowId);

    @GET
    @Path("/objectives")
    List<ObjectiveDto> getAllObjectives();
}
