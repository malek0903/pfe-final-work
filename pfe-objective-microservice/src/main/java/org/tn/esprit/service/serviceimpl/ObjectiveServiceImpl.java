package org.tn.esprit.service.serviceimpl;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.tn.esprit.client.CategorieRestClient;
import org.tn.esprit.client.WorkflowRestClient;
import org.tn.esprit.commons.dto.ObjectiveDto;
import org.tn.esprit.dao.ObjectiveRepository;
import org.tn.esprit.entities.Objective;
import org.tn.esprit.service.ObjectiveService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
@Slf4j
public class ObjectiveServiceImpl implements ObjectiveService {
    @Inject
    ObjectiveRepository objectiveRepository;

    @Inject
    @RestClient
    WorkflowRestClient workflowRestClient ;


    @Override
    public void createObjective(ObjectiveDto objectiveDto) {
        Long workflowId = workflowRestClient.getByWorkflowName(objectiveDto.getWorkflowName()).getWorkflowId();
            objectiveRepository.save(new Objective(
                    objectiveDto.getName(),
                    workflowId,
                    objectiveDto.getWorkflowCategorieId()
            ));

    }

    @Override
    public ObjectiveDto getById(Long id) {
        return mapToDo(objectiveRepository.findById(id).orElse(new Objective()));
    }

    @Override
    public List<ObjectiveDto> getAllObjectives() {
        return objectiveRepository.findAll().stream().map(ObjectiveServiceImpl::mapToDo).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllObjectivesByCategorieIdAndWorkflowId(Long categorieId, Long workfowId) {

        List<String> allObjectives = new ArrayList<>();
        objectiveRepository.findAll().stream().filter(distinctByKey(Objective::getName))
                .forEach(objective -> {
            if (objective.getIdCategorie().longValue() == categorieId.longValue() &&
                objective.getIdWorkflow().longValue() == workfowId.longValue() ) {
                allObjectives.add(objective.getName());
            }
        });
        return allObjectives;
    }

    @Override
    public List<String> getAllObjectivesByCategorieId(Long categoryId) {
        List<String> allObjectives = new ArrayList<>();
        objectiveRepository.findAll().stream().filter(distinctByKey(Objective::getName))
                .forEach(objective -> {
                    if (objective.getIdCategorie().longValue() == categoryId.longValue()) {
                        allObjectives.add(objective.getName());
                    }
                });
        return allObjectives;
    }

    @Override
    public Long countAll() {
        return objectiveRepository.count();
    }

    public static ObjectiveDto mapToDo(Objective objective) {
        return new ObjectiveDto(
                objective.getId(),
                objective.getName(),
                objective.getIdCategorie(),
                objective.getIdWorkflow()
        );
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
