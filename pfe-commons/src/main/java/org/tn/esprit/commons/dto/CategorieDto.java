package org.tn.esprit.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorieDto {
    private Long categorieId ;
    private String categorieName ;
    private List<String> workflowsName ;

    private List<String> objectifs;


}
