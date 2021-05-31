package org.tn.esprit.ressource;


import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;
import org.tn.esprit.commons.dto.ComponentDto;
import org.tn.esprit.commons.dto.WorkflowDto;
import org.tn.esprit.service.ComponentService;
import org.tn.esprit.service.impl.UserWorkflowAffectationService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("components")
//@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Tags(value = @Tag(name = "Component", description = "All the component methods"))
public class ComponentController {


    @Inject
    ComponentService componentService;


    @Inject
    UserWorkflowAffectationService userWorkflowAffectationService ;

    @GET
    @Path("{workflow}/user/{user}/step/{step}")
    @Produces(MediaType.APPLICATION_JSON)
    public void UserComponentUpdateStep(@PathParam(value = "workflow") Long workflow, @PathParam(value = "user") String userName, @PathParam(value = "step") int step) {
        userWorkflowAffectationService.UserComponentUpdateStep(workflow, userName, step);
    }

    @GET
    @Path("progress/{workflow}/workflow/{user}/user")
    @Produces(MediaType.APPLICATION_JSON)
    public double getUserComponentStepProgress(@PathParam(value = "workflow") Long workflow, @PathParam(value = "user") String userName){
        return userWorkflowAffectationService.getUserComponentStepProgress(workflow,userName);
    }

    @POST
    @Path("workflow-users-progress")
    @Produces(MediaType.APPLICATION_JSON)
    public double getAllUserComponentStepProgress(@RequestBody WorkflowDto workflowDto){
        return userWorkflowAffectationService.getAllUserComponentStepProgress(workflowDto);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createWorkflow(@RequestBody ComponentDto component){
        System.out.println(component);
        componentService.saveComponent(component);
        return  Response.status(Response.Status.CREATED).entity("votre Composant a été ajouté avec succés").build();
    }

    @GET
    @Path("step/{workflow}/workflow/{user}/user")
    @Produces(MediaType.APPLICATION_JSON)
    public int findUserComponentStepProgress(@PathParam(value = "workflow") Long workflow, @PathParam(value = "user") String userName){
        return userWorkflowAffectationService.findUserCompnentStep(workflow,userName);
    }

}
