package org.tn.esprit.service;
import org.tn.esprit.commons.dto.WorkflowDto;

import java.util.List;

public interface WorkflowService {

    Long createWorkflow(WorkflowDto workflow);

    List<WorkflowDto> getAllWorkflow();
    WorkflowDto getWorkflowByName(String workflowName);
    List<WorkflowDto> getWorkflowByUserCreator(String username);
    List<WorkflowDto> getAllWorkflowByUserCreator(String username);
    List<WorkflowDto> getWorkflowByUserValidator(String username);
    List<WorkflowDto> getWorkflowPrototypeByCategoryName(String categoryName);
    List<String> getOnlyWorkflowsName();
    void updateWorkflow(WorkflowDto workflowDto);
    void deleteWorkflow(String workflowName);
    void updateWorkflowPrototypeStatus(String workflowName);
    void beforeDeletingWorkflow(String workflowName);
   // List<String> getOnlyWorkflowsCategorie();


}
