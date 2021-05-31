package org.tn.esprit.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.tn.esprit.commons.dto.UserDto;
import org.tn.esprit.commons.dto.WorkflowDto;
import org.tn.esprit.dao.UserRepository;
import org.tn.esprit.dao.UserWorkflowAffectationRepository;
import org.tn.esprit.dao.WorkflowRepository;
import org.tn.esprit.commons.dto.UserWorkflowAffectationDto;
import org.tn.esprit.entities.User;
import org.tn.esprit.entities.UserWorkflowAffectation;
import org.tn.esprit.entities.Workflow;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@ApplicationScoped
@Transactional
public class UserWorkflowAffectationService {


    @Inject
    UserWorkflowAffectationRepository userWorkflowAffectationRepository;
    @Inject
    UserRepository userRepository ;
    @Inject
    WorkflowRepository workflowRepository ;
    public static UserWorkflowAffectationDto mapToDto(UserWorkflowAffectation userWorkflow){
        return  new UserWorkflowAffectationDto(
                UserService.mapToDto(userWorkflow.getUser()),
                WorkflowServiceImpl.mapToDto(userWorkflow.getWorkflow()),
                userWorkflow.getUserComponentStep()
                );
    }

    public void UserComponentUpdateStep(Long workflowId, String username , int step){
        User user = userRepository.findUserByUsername(username) ;
        Workflow workflow = workflowRepository.findById(workflowId).orElse(null);
        UserWorkflowAffectation userWorkflowAffectation = userWorkflowAffectationRepository.findByWorkflowAndUser(workflow,user);
        userWorkflowAffectation.setUserComponentStep(userWorkflowAffectation.getUserComponentStep()+1);
        userWorkflowAffectationRepository.save(userWorkflowAffectation);
    }

    public  double getUserComponentStepProgress(Long workflowId, String userName ){
        User user = userRepository.findUserByUsername(userName) ;
        Workflow workflow = workflowRepository.findById(workflowId).orElse(null);
        UserWorkflowAffectation userWorkflowAffectation = userWorkflowAffectationRepository.findByWorkflowAndUser(workflow,user);

        return (userWorkflowAffectation.getUserComponentStep() *100) / workflow.getComponents().size() ;

    }

    public int findUserCompnentStep(Long workflowId, String username ){
        User user = userRepository.findUserByUsername(username) ;
        Workflow workflow = workflowRepository.findById(workflowId).orElse(null);
        return userWorkflowAffectationRepository.findByWorkflowAndUser(workflow,user).getUserComponentStep();
    }


    public double getAllUserComponentStepProgress(WorkflowDto workflowDto){

        double allUserComponentStepProgress = 0;
        for (UserDto user : workflowDto.getUsersDto()){
           allUserComponentStepProgress +=   getUserComponentStepProgress(workflowDto.getWorkflowId(),user.getUsername());

        }
        return  allUserComponentStepProgress / workflowDto.getUsersDto().size();
}




}
