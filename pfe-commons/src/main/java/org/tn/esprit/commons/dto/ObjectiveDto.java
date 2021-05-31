package org.tn.esprit.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ObjectiveDto {

    private Long idObjective ;
    private String name;
    private Long workflowCategorieId ;
    private Long idWorkflow ;
    private String workflowName;


    public ObjectiveDto(Long idObjective, String name, Long workflowCategorieId, Long idWorkflow) {
        this.idObjective = idObjective;
        this.name = name;
        this.workflowCategorieId = workflowCategorieId;
        this.idWorkflow = idWorkflow;
    }
}
