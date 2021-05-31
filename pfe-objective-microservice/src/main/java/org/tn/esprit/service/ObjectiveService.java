package org.tn.esprit.service;


import org.tn.esprit.commons.dto.ObjectiveDto;

import java.util.List;

public interface ObjectiveService {

    void createObjective(ObjectiveDto objectiveDto);
    ObjectiveDto getById(Long id) ;
    List<ObjectiveDto> getAllObjectives();
    List<String> getAllObjectivesByCategorieIdAndWorkflowId(Long categorieName,Long workflowId);
    List<String> getAllObjectivesByCategorieId(Long categoryId);
    Long  countAll() ;
}
