package org.tn.esprit.client;


import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.tn.esprit.commons.dto.WorkflowDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("")
@RegisterRestClient
@CircuitBreaker(requestVolumeThreshold = 10, delay = 15000)
@Retry(maxRetries = 4)
@Timeout(500)
@Produces(MediaType.APPLICATION_JSON)
public interface WorkflowRestClient {


    @GET
    @Path("/workflows/{name}")
    WorkflowDto getByWorkflowName(@PathParam(value = "name") String name);


    @GET
    @Path("/workflows")
    List<WorkflowDto> getAllWorkflow();

    @GET
    @Path("/workflows/{user-creator}/user-creator-workflows")
    List<WorkflowDto> getUserCreatorWorkflow(@PathParam(value = "user-creator") String username);

    @GET
    @Path("/workflows/{user-creator}/user-creator-all-workflows")
    List<WorkflowDto> getAllUserCreatorWorkflow(@PathParam(value = "user-creator") String username);
}
