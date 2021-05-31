package org.tn.esprit.controller;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.tn.esprit.client.WorkflowRestClient;
import org.tn.esprit.commons.dto.ReviewDto;
import org.tn.esprit.commons.dto.WorkflowDto;
import org.tn.esprit.service.ReviewService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("reviews")
@Produces(MediaType.APPLICATION_JSON)
@Tags(value = @Tag(name = "Reviews", description = " All Reviews methods"))
public class ReviewController {

    @Inject
    @RestClient
    WorkflowRestClient workflowRestClient ;

    @Inject
    ReviewService reviewService ;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createReview(@RequestBody(name = "review") ReviewDto reviewDto) {
        reviewService.createReview(reviewDto);
    }

    @GET
    @Path("{workflowId}/{username}")
    public Long getReviewsByIdWorkflowAndUsername(@PathParam(value = "workflowId") Long workflowId ,@PathParam(value = "username") String username){
        return reviewService.getReviewsByWorkflowId(workflowId,username);
    }

    @GET
    @Path("/name/{name}")
    public WorkflowDto name(@PathParam(value = "name") String name) {
        return workflowRestClient.getByWorkflowName(name);
    }

    @GET
    public List<ReviewDto> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GET
    @Path("/count")
    public Long countAllProducts() {
        return this.reviewService.countAll();
    }


    @GET
    @Path("{workflowId}")
    public boolean existByWorkflowId(@PathParam(value = "workflowId") Long workflowId){
        return reviewService.existByWorkflowId(workflowId);
    }

}
