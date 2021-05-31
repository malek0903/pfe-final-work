package org.tn.esprit.ressource;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;
import org.tn.esprit.dto.AdminDashboardDto;
import org.tn.esprit.service.AdminService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("admin-dashboards")
@Tags(value = @Tag(name = "Admin dashboards", description = " All Admin dashboards methods"))
@Produces(MediaType.APPLICATION_JSON)
public class AdminResource {



    @Inject
    AdminService adminService ;

    @GET
    @Path("{username}/{workflowType}/{workflowCategoryName}")
    public List<AdminDashboardDto> maxRatingPerWorkflow(
            @PathParam(value = "username") String username,
            @PathParam(value = "workflowType") String workflowType,
            @PathParam(value = "workflowCategoryName") String workflowCategoryName) {
        return adminService.maxRatingPerWorkflow(username,workflowType,workflowCategoryName);
    }

    @GET
    @Path("{username}")
    public List<AdminDashboardDto> maxNbUsersByWorkflow(@PathParam(value = "username") String username) {
        return adminService.nbUsersPerWorkflow(username);
    }

    @GET
    @Path("{username}/workflows-category")
    public List<AdminDashboardDto> maxNbWorkflowByCategory(@PathParam(value = "username") String username) {
        return adminService.nbWorkflowByCategory(username);
    }

    @GET
    @Path("{username}/workflows-objectives/{categoryName}")
    public List<AdminDashboardDto> nbWorkflowByObjectives(@PathParam(value = "username") String username
            ,@PathParam(value = "categoryName") String categoryName) {
        return adminService.objectivesByWorkflowAndPerCategory(username,categoryName);
    }


}
