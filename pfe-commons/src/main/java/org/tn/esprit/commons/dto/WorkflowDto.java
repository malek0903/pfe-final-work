package org.tn.esprit.commons.dto;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowDto implements Serializable {
    private Long  workflowId ;
    private String workflowCreatorName;
    private String workflowName;
    private String categorieName ;
    private boolean prototype;
    private String workflowPrototypeStatus;
    private Long categorieId;
    private String status;
    private Instant createdDate;
    private List<ComponentDto> componentsDto;
    private Set<UserDto> usersDto ;
    private boolean deleted;
    private boolean updated;

}
