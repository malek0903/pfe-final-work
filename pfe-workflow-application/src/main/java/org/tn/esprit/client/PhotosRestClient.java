package org.tn.esprit.client;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/search")
@RegisterRestClient
public interface PhotosRestClient {

    @GET
    @Path("/photos")
    @Produces(MediaType.APPLICATION_JSON)
    String getImagesUrl(@QueryParam(value = "query") String query ,
                           @QueryParam(value = "client_id") String clientId );
}
