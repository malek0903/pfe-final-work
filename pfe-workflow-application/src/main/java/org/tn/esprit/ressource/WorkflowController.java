package org.tn.esprit.ressource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.json.JSONObject;
import org.tn.esprit.client.ConsulRestClient;
import org.tn.esprit.client.ObjectiveRestClient;
import org.tn.esprit.commons.dto.ConsulServiceData;
import org.tn.esprit.commons.dto.ObjectiveDto;
import org.tn.esprit.commons.dto.WorkflowDto;
import org.tn.esprit.service.WorkflowService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("workflows")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = " Workflow", description = "All the workflow methods")
public class WorkflowController {

  @Inject
  @RestClient
  ConsulRestClient consulRestClient;

  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  public void registerService(@RequestBody ConsulServiceData consulServiceData) throws JsonProcessingException {
    System.out.println("this is consul data:: " + consulServiceData.toString());

    String objectToBeSent = JSONObject.valueToString(consulServiceData);

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String json = ow.writeValueAsString(consulServiceData);

    System.out.println("this is consul objectToBeSent :: " + json);
    consulRestClient.serviceRegistration(json);
  }

  @Inject
  WorkflowService workflowService;


  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createWorkflow(@RequestBody WorkflowDto workflow) {
   Long idWorkflow =  workflowService.createWorkflow(workflow);
    return Response.status(Response.Status.CREATED).entity(idWorkflow).build();

  }

  @POST
  @Path("update")
  @Consumes(MediaType.APPLICATION_JSON)
  public void updateWorkflow(@RequestBody WorkflowDto workflowDto) {
    workflowService.updateWorkflow(workflowDto);
  }

  @GET
  public List<WorkflowDto> getAllWorkflow() {
    return workflowService.getAllWorkflow();
  }

  @GET
  @Path("{workflowName}")
  public WorkflowDto getWorkflowByName(@PathParam(value = "workflowName") String workflowName) {
    return workflowService.getWorkflowByName(workflowName);
  }


  @GET
  @Path("{user-creator}/user-creator-workflows")
  public List<WorkflowDto> getWorkflowByUserCreator(@PathParam(value = "user-creator") String userCreator) {
    return workflowService.getWorkflowByUserCreator(userCreator);
  }


  @GET
  @Path("{user-creator}/user-creator-all-workflows")
  public List<WorkflowDto> getAllWorkflowByUserCreator(@PathParam(value = "user-creator") String userCreator) {
    return workflowService.getAllWorkflowByUserCreator(userCreator);
  }

  @GET
  @Path("{user-validator}/user-validator-workflows")
  public List<WorkflowDto> getWorkflowByUserValidator(@PathParam(value = "user-validator") String userCreator) {
    return workflowService.getWorkflowByUserValidator(userCreator);
  }
  @GET
  @Path("{category-name}/category-workflows")
  public List<WorkflowDto> getWorkflowByCategoryName(@PathParam(value = "category-name") String categoryName) {
    return workflowService.getWorkflowPrototypeByCategoryName(categoryName);
  }

  @GET
  @Path("only-workflow-name")
  public List<String> getOnlyWorkflowsName(){
    return  workflowService.getOnlyWorkflowsName();
  }

  @DELETE
  @Path("{workflowName}")
  public void deleteWorkflow(@PathParam(value = "workflowName") String workflowName){
    workflowService.deleteWorkflow(workflowName);
  }

  @GET
  @Path("{workflowName}/before-deleting")
  public Response beforeDeletingWorkflow(@PathParam(value = "workflowName") String workflowName){
    workflowService.beforeDeletingWorkflow(workflowName);
    return Response.status(Response.Status.OK).entity("archive before deleting workflow").build();
  }

  @GET
  @Path("{workflowName}/workflow-prototype")
  public Response updateWorkflowPrototypeStatus(@PathParam(value = "workflowName") String workflowName){
    workflowService.updateWorkflowPrototypeStatus(workflowName);
    return Response.status(Response.Status.OK).entity("Workflow prototype Status updated").build();
  }





}
