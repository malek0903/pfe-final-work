package org.tn.esprit.commons.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizOptionDto {
    private Long id;
    private int optionValue ;
    private String optionText ;

}
