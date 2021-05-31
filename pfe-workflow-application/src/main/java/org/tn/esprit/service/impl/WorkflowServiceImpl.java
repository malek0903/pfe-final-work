package org.tn.esprit.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.tn.esprit.commons.dto.*;
import org.tn.esprit.dao.ComponentRepository;
import org.tn.esprit.dao.UserWorkflowAffectationRepository;
import org.tn.esprit.dao.WorkflowRepository;
import org.tn.esprit.entities.Categorie;
import org.tn.esprit.entities.Workflow;
import org.tn.esprit.entities.enumeration.Status;
import org.tn.esprit.service.CategorieService;
import org.tn.esprit.service.WorkflowService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@Transactional
public class WorkflowServiceImpl implements WorkflowService {

    @Inject
    WorkflowRepository workflowRepository;
    @Inject
    ComponentServiceImpl componentService ;
    @Inject
    UserService userService;

    @Inject
    UserWorkflowAffectationRepository userWorkflowAffectationRepository ;
    @Inject
    CategorieService categorieService ;

    @Inject
    QuizService quizService;

    @Inject
    ComponentRepository componentRepository;

    @Override
    @Transactional
    public Long createWorkflow(WorkflowDto workflowDto) {

       Categorie categorie = categorieService.saveCategorieIfNotExist(workflowDto.getCategorieName());

        workflowRepository.save(new Workflow(
                workflowDto.getWorkflowName(),
                workflowDto.isPrototype(),
                workflowDto.getWorkflowPrototypeStatus(),
                categorie,
                Status.valueOf(workflowDto.getStatus()),
                workflowDto.getWorkflowCreatorName()
        ));
        Workflow workflow = workflowRepository.findWorkflowByWorkflowName(workflowDto.getWorkflowName());
        userService.saveUsers(workflowDto.getUsersDto(), workflow);
        componentService.prepareComponentToBeSaved(workflowDto.getComponentsDto(), workflow);
        return workflow.getId();

    }

    @Override
    @Transactional
    public void updateWorkflow(WorkflowDto workflowDto) {
        Categorie categorie = categorieService.saveCategorieIfNotExist(workflowDto.getCategorieName());
        if(workflowDto.getWorkflowId() != null){
            Workflow workflow =  workflowRepository.findById(workflowDto.getWorkflowId()).orElse(null);
            assert workflow != null;
            workflow.setCategorie(categorie);
            workflow.setStatus(Status.valueOf(workflowDto.getStatus()));
            workflow.setUpdated(true);
            workflowRepository.save(workflow);
            userService.updateOrDeleteUserIfExist(workflowDto.getUsersDto(),workflow);
            componentService.updateOrAddOrDeleteComponents(workflowDto.getComponentsDto(), workflow);
        }

        else {
           Workflow workflow = new Workflow(
                    workflowDto.getWorkflowName(),
                    workflowDto.isPrototype(),
                    workflowDto.getWorkflowPrototypeStatus(),
                    categorie,
                    Status.valueOf(workflowDto.getStatus()),
                    workflowDto.getWorkflowCreatorName()
            );
            workflow.setCategorie(categorie);
            workflow.setStatus(Status.valueOf(workflowDto.getStatus()));
            System.out.println("workflow prototype to be saved" +workflow.getId() +"  /  "+ workflow.getWorkflowName());
            workflowRepository.save(workflow);
            userService.saveUsers(workflowDto.getUsersDto(), workflow);
            componentService.prepareComponentToBeSaved(workflowDto.getComponentsDto(), workflow);
        }


    }



    @Transactional
    @Override
    public void deleteWorkflow(String workflowName) {
        Workflow workflow = workflowRepository.findWorkflowByWorkflowName(workflowName);
        userWorkflowAffectationRepository.deleteAllByWorkflow(workflow);
        workflow.getComponents().forEach(component -> {
            quizService.deleteAllQuizByComponent(component.getQuizzes());
        });
        componentRepository.deleteAllByWorkflow(workflow);
        workflowRepository.delete(workflow);
    }

    @Override
    public void updateWorkflowPrototypeStatus(String workflowName) {
        Workflow workflow = workflowRepository.findWorkflowByWorkflowName(workflowName);
        workflow.setWorkflowPrototypeStatus("DONE");
        workflowRepository.save(workflow);
    }

    @Override
    public void beforeDeletingWorkflow(String workflowName) {
        Workflow workflow = workflowRepository.findWorkflowByWorkflowName(workflowName);
        workflow.setUpdated(false);
        workflow.setDeleted(true);
        workflowRepository.save(workflow);
    }


    @Override
    public List<WorkflowDto> getAllWorkflow() {
        return this.workflowRepository.findAll()
                .stream().map(WorkflowServiceImpl::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public WorkflowDto getWorkflowByName(String workflowName) {
        try {
            return mapToDto(
                    this.workflowRepository.findWorkflowByWorkflowName(workflowName));

        }catch (NullPointerException nullPointerException){
            return new WorkflowDto();
        }
       }

    @Override
    public List<WorkflowDto> getWorkflowByUserCreator(String username) {
        List<WorkflowDto> workflowDtos = new ArrayList<>();
        workflowRepository.findAll().stream().forEach(workflow -> {
            if (workflow.getWorkflowCreatorUsername().equals(username) &&
                !workflow.isPrototype()) {
                workflowDtos.add(mapToDto(workflow));
            }
        });
        return workflowDtos;
    }
    @Override
    public List<WorkflowDto> getAllWorkflowByUserCreator(String username) {
        List<WorkflowDto> workflowDtos = new ArrayList<>();
        workflowRepository.findAll().stream().forEach(workflow -> {
            if (workflow.getWorkflowCreatorUsername().equals(username)) {
                workflowDtos.add(mapToDto(workflow));
            }
        });
        return workflowDtos;
    }


    @Override
    public List<WorkflowDto> getWorkflowByUserValidator(String username) {
        List<WorkflowDto> workflowDtos = new ArrayList<>();
        workflowRepository.findAll().forEach(workflow -> {
            workflow.getUserWorkflowAffectations()
                    .forEach(item -> {
                if (item.getUser().getUsername().equals(username)) {
                    workflowDtos.add(mapToDto(workflow));
                }
            });
        });
        for (WorkflowDto workflow : workflowDtos){
            List<ComponentDto> componentsDto = workflow.getComponentsDto().stream()
                    .sorted(Comparator.comparingInt(ComponentDto::getStep)).collect(Collectors.toList());
            //sot quiz by quiz Id
            for (ComponentDto componentDto : componentsDto) {
                List<QuizDto> quizDtos = componentDto.getQuizQuestion().stream()
                        .sorted(Comparator.comparingLong(QuizDto :: getQuestionId)).collect(Collectors.toList());
                List<FieldDto> fieldDtos = componentDto.getFields().stream()
                        .sorted(Comparator.comparingLong(FieldDto::getId)).collect(Collectors.toList());
                componentDto.setFields(fieldDtos);
                componentDto.setQuizQuestion(quizDtos);
            }

            workflow.setComponentsDto(componentsDto);
        }
        return workflowDtos ;
    }

    @Override
    public List<WorkflowDto> getWorkflowPrototypeByCategoryName(String categoryName) {
        List<WorkflowDto> workflowDtos = new ArrayList<>();
        workflowRepository.findAll().forEach(workflow -> {
            if (workflow.getCategorie().getCategorieName().equals(categoryName) && workflow.isPrototype()) workflowDtos.add(mapToDto(workflow));
        });
        return workflowDtos;
    }

    @Override
    public List<String> getOnlyWorkflowsName() {
        List<String> onlyWorkflowsName = new ArrayList<>();
        workflowRepository.findAll().forEach(workflow ->
                onlyWorkflowsName.add(workflow.getWorkflowName())
                );
        return onlyWorkflowsName;
    }




    public static WorkflowDto mapToDto(Workflow workflow) {
        List<ComponentDto> componentsDto = workflow.getComponents()
                .stream().map(ComponentServiceImpl::mapToDto).collect(Collectors.toList());
        Set<UserDto> usersDto = new HashSet<>();
        workflow.getUserWorkflowAffectations().forEach(item -> {
            usersDto.add(UserService.mapToDto(item.getUser()));
        });
        return new WorkflowDto(
                workflow.getId(),
                workflow.getWorkflowCreatorUsername(),
                workflow.getWorkflowName(),
                workflow.getCategorie().getCategorieName(),
                workflow.isPrototype(),
                workflow.getWorkflowPrototypeStatus(),
                workflow.getCategorie().getId(),
                workflow.getStatus().name(),
                workflow.getCreatedDate(),
                componentsDto,
                usersDto,
                workflow.isDeleted(),
                workflow.isUpdated()

        );
    }



}
