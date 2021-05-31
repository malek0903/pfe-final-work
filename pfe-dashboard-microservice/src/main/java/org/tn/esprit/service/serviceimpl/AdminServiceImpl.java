package org.tn.esprit.service.serviceimpl;

import io.netty.util.HashingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.tn.esprit.client.ObjectiveRestClient;
import org.tn.esprit.client.WorkflowRestClient;
import org.tn.esprit.commons.dto.ObjectiveDto;
import org.tn.esprit.commons.dto.ReviewDto;
import org.tn.esprit.commons.dto.WorkflowDto;
import org.tn.esprit.dto.AdminDashboardDto;
import org.tn.esprit.service.AdminService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
@Transactional
public class AdminServiceImpl implements AdminService {

    @Inject
    @RestClient
    WorkflowRestClient workflowRestClient;

    @Inject
    @RestClient
    ObjectiveRestClient objectiveRestClient;

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @Override
    public List<AdminDashboardDto> maxRatingPerWorkflow(String username, String workflowType, String workflowCategoryName) {
        List<AdminDashboardDto> adminDashboards = new ArrayList<>();
        workflowRestClient.getAllUserCreatorWorkflow(username).forEach(workflow -> {
            if (objectiveRestClient.existById(workflow.getWorkflowId())) {
                if (workflowType.equals("Prototype")) {
                    if (workflow.isPrototype()) {
                        AdminDashboardDto adminDashboardDto = new AdminDashboardDto();
                        adminDashboardDto.setRatingNumber(calculateRatingByWorkflow(workflow.getWorkflowId()));
                        adminDashboardDto.setWorkflowName(workflow.getWorkflowName());
                        adminDashboards.add(adminDashboardDto);
                    }
                }
                if (workflowType.equals("Workflow")) {
                    if (!workflow.isPrototype()) {
                        if (workflow.getCategorieName().equals(workflowCategoryName)) {
                            AdminDashboardDto adminDashboardDto = new AdminDashboardDto();
                            adminDashboardDto.setRatingNumber(calculateRatingByWorkflow(workflow.getWorkflowId()));
                            adminDashboardDto.setWorkflowName(workflow.getWorkflowName());
                            adminDashboards.add(adminDashboardDto);
                        }
                        if (workflowCategoryName.equals("AllCategories")) {
                            AdminDashboardDto adminDashboardDto = new AdminDashboardDto();
                            adminDashboardDto.setRatingNumber(calculateRatingByWorkflow(workflow.getWorkflowId()));
                            adminDashboardDto.setWorkflowName(workflow.getWorkflowName());
                            adminDashboards.add(adminDashboardDto);
                        }
                    }
                }

            }
        });
        return adminDashboards;
    }


    public double calculateRatingByWorkflow(Long workflowId) {
        double reviewSum = 0;
        List<ReviewDto> reviews = objectiveRestClient.getAllReviews().stream()
                .filter(reviewDto -> reviewDto.getIdWorkflow().longValue() == workflowId.longValue())
                .collect(Collectors.toList());
        for (ReviewDto review : reviews) {
            reviewSum += review.getRaiting().floatValue();
        }
        return reviewSum / reviews.size();
    }

    @Override
    public List<AdminDashboardDto> nbUsersPerWorkflow(String username) {
        List<AdminDashboardDto> adminDashboards = new ArrayList<>();
        workflowRestClient.getUserCreatorWorkflow(username).forEach(workflow -> {
            AdminDashboardDto adminDashboardDto = new AdminDashboardDto();
            adminDashboardDto.setWorkflowName(workflow.getWorkflowName());
            adminDashboardDto.setNbUsers(workflow.getUsersDto().size());
            adminDashboards.add(adminDashboardDto);
        });
        if(adminDashboards.size() > 3){
            return adminDashboards.stream()
                    .sorted((o1, o2) -> Integer.compare( o2.getNbUsers(),o1.getNbUsers()) )
                    .collect(Collectors.toList()).subList(0,3);

        }else {
            return adminDashboards.stream()
                    .sorted((o1, o2) -> Integer.compare( o2.getNbUsers(),o1.getNbUsers()) )
                    .collect(Collectors.toList());
        }
        }

    @Override
    public List<AdminDashboardDto> nbWorkflowByCategory(String username) {
        List<AdminDashboardDto> adminDashboards = new ArrayList<>();
        workflowRestClient.getUserCreatorWorkflow(username).stream()
                .collect(Collectors.groupingBy(WorkflowDto::getCategorieName))
                .forEach((categoryName, workflowDtos) -> {
                    AdminDashboardDto adminDashboard = new AdminDashboardDto();
                    adminDashboard.setNbWorkflow(workflowDtos.size());
                    adminDashboard.setCategoryName(categoryName);
                    adminDashboards.add(adminDashboard);
                });
        return adminDashboards.stream()
                .sorted(Comparator.comparingInt(AdminDashboardDto::getNbWorkflow))
                .collect(Collectors.toList());
    }

    @Override
    public List<AdminDashboardDto> objectivesByWorkflowAndPerCategory(String username, String categoryName) {
        List<WorkflowDto> workflowDtos = workflowRestClient.getUserCreatorWorkflow(username);
        List<ObjectiveDto> objectivesDto = objectiveRestClient.getAllObjectives();
        List<AdminDashboardDto> adminDashboards = new ArrayList<>();
        objectivesDto.stream()
                .collect(Collectors.groupingBy(ObjectiveDto::getName))
                .forEach((objectiveName, objectiveDtos) -> {
                    for (ObjectiveDto objective : objectiveDtos) {
                        for (WorkflowDto workflowDto : workflowDtos) {
                            if (workflowDto.getWorkflowId().equals(objective.getIdWorkflow())) {
                                if(categoryName.equals(workflowDto.getCategorieName())){
                                    AdminDashboardDto adminDashboardDto = new AdminDashboardDto();
                                    adminDashboardDto.setObjectivesCount(objectiveDtos.size());
                                    adminDashboardDto.setObjectivesName(objectiveName);
                                    adminDashboards.add(adminDashboardDto);
                                }
                                if(categoryName.equals("All categories")){
                                    AdminDashboardDto adminDashboardDto = new AdminDashboardDto();
                                    adminDashboardDto.setObjectivesCount(objectiveDtos.size());
                                    adminDashboardDto.setObjectivesName(objectiveName);
                                    adminDashboards.add(adminDashboardDto);
                                }
                            }
                        }
                    }
                });

        return adminDashboards.stream()
                .sorted(Comparator.comparingInt(AdminDashboardDto::getObjectivesCount))
                .collect(Collectors.toList()).stream()
                .filter(distinctByKey(AdminDashboardDto::getObjectivesName)).collect(Collectors.toList());

    }


}
