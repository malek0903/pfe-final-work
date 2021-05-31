package org.tn.esprit.client;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.tn.esprit.commons.dto.ObjectiveDto;
import org.tn.esprit.commons.dto.WorkflowDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

@Path("/objectives")
@RegisterRestClient
public interface ObjectiveRestClient {


    @GET
    @Path("/{id}")
    @CircuitBreaker(requestVolumeThreshold = 10, delay = 15000)
    @Retry(maxRetries = 4)
    @Timeout(500)
    @Produces(MediaType.APPLICATION_JSON)
    ObjectiveDto getByObjectiveName(@PathParam Long id);


    @GET
    @Path("/categories/{categoryId}")
    @CircuitBreaker(requestVolumeThreshold = 10, delay = 15000)
    @Retry(maxRetries = 4)
    @Timeout(500)
    @Produces(MediaType.APPLICATION_JSON)
    List<String>   getAllObjectivesByCategorieId(@PathParam Long categoryId);


}
