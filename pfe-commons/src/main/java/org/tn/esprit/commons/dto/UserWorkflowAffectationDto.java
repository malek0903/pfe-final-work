package org.tn.esprit.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWorkflowAffectationDto implements Serializable {


    private Long id ;
    private UserDto userDto;
    private WorkflowDto workflowDto ;
    private int userComponentStep ;

    public UserWorkflowAffectationDto(UserDto userDto, WorkflowDto workflowDto , int userComponentStep ) {
        this.userDto = userDto;
        this.workflowDto = workflowDto;
        this.userComponentStep = userComponentStep ;
    }
}
