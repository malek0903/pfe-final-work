package org.tn.esprit.client;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.tn.esprit.commons.dto.ConsulServiceData;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/agent/service/")
@RegisterRestClient
public interface ConsulRestClient {

    @Path("/register?replace-existing-checks=true")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response serviceRegistration(@RequestBody String consulServiceData);

    @Path("/deregister/{serviceId}")
    @PUT
    void serviceDeregister(@PathParam(value = "serviceId") String serviceId);
}
