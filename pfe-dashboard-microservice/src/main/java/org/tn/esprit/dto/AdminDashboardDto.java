package org.tn.esprit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardDto {

    private double ratingNumber;
    private String workflowName;
    private int nbUsers;
    private int nbWorkflow;
    private String categoryName;
    private String objectivesName ;
    private int objectivesCount;
}
