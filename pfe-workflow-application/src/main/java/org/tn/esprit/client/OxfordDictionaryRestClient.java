package org.tn.esprit.client;


import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/json")
@RegisterRestClient
public interface OxfordDictionaryRestClient {

    @GET
    @Path("/{word}")
    @Produces(MediaType.APPLICATION_JSON)
    String getSynonymeWord(@PathParam String word , @QueryParam(value = "key") String key);
}
