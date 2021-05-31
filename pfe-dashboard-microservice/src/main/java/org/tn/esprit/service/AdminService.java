package org.tn.esprit.service;

import org.tn.esprit.dto.AdminDashboardDto;

import java.util.List;
import java.util.Map;

public interface AdminService {

    List<AdminDashboardDto> maxRatingPerWorkflow(String username,String workflowType,String workflowCategoryName);
    List<AdminDashboardDto> nbUsersPerWorkflow(String username);
    List<AdminDashboardDto> nbWorkflowByCategory(String username);
    List<AdminDashboardDto> objectivesByWorkflowAndPerCategory(String username, String categoryName);
   /* void maxRatingPerWorkflowByCategory();
    void topFiveUsersByWorkflowWithValidTrueAndHasMaxObjectiveByCategory();*/
}
