package org.tn.esprit.ressource;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.tn.esprit.client.ObjectiveRestClient;
import org.tn.esprit.commons.dto.ObjectiveDto;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/objective")
public class ObjectiveController {

    @Inject
    @RestClient
    ObjectiveRestClient objectiveRestClient ;

    @GET
    @Path("/name/{id}")
    public ObjectiveDto name(@PathParam Long id) {
        return objectiveRestClient.getByObjectiveName(id);
    }

}
