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
public class QuizDto {
    private Long id;
    private Long questionId ;
    private String questionText ;
    private int answer ;
    private String explanation;
    private int selectedOption ;
    private List<QuizOptionDto> options ;

}
