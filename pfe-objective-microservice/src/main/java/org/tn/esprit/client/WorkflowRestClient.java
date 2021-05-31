package org.tn.esprit.client;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.tn.esprit.commons.dto.WorkflowDto;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/workflows")
@RegisterRestClient
public interface WorkflowRestClient {


    @GET
    @Path("/{name}")
    @CircuitBreaker(requestVolumeThreshold = 10, delay = 15000)
    @Retry(maxRetries = 4)
    @Timeout(500)
    @Produces(MediaType.APPLICATION_JSON)
    WorkflowDto getByWorkflowName(@PathParam String name);
}
