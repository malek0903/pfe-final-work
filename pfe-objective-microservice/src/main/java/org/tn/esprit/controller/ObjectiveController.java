package org.tn.esprit.controller;

import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;
import org.tn.esprit.commons.dto.ObjectiveDto;
import org.tn.esprit.service.ObjectiveService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("objectives")
@Tags(value = @Tag(name = "Objectives", description = " All Reviews methods"))
public class ObjectiveController {


    @Inject
    ObjectiveService objectiveService ;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    //@Authenticated
    public void createObjective(ObjectiveDto objectiveDto) {
        objectiveService.createObjective(objectiveDto);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ObjectiveDto getObjectById(@PathParam(value = "id") Long id){
        return objectiveService.getById(id);

    }

    @GET
    @Path("categories/{categoryId}/workflow/{workflowId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAllObjectivesByCategorieIdAndWorkflowId(@PathParam(value = "categoryId") Long categorieId,
                                                      @PathParam(value = "workflowId") Long workflowId){
        return objectiveService.getAllObjectivesByCategorieIdAndWorkflowId(categorieId,workflowId);
    }



    @GET
    @Path("categories/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAllObjectivesByCategorieId(@PathParam(value = "categoryId") Long categoryId){
        return objectiveService.getAllObjectivesByCategorieId(categoryId);
    }


    @GET
    @Path("/count")
    public Long countAllProducts() {
        return this.objectiveService.countAll();
    }

    @GET
    public List<ObjectiveDto> getAllObjectives() {
        return this.objectiveService.getAllObjectives();
    }


}
