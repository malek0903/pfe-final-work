package org.tn.esprit.commons.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComponentDto implements Serializable {
    private Long id ;
    private String name;
    private String sourceId;
    private String destinationId;
    private String link;
    private String type;
    private String file;
    private String fileType;
    private int step ;
    private String textColor;
    private String backgroundColor;
    private Boolean bold;
    private Boolean italic;
    private Boolean underline ;
    private int fontSize ;
    private String fontFamily ;
    private Long workflowDtoId ;
    private Integer quizScore ;
    private Float timePerQuestion;
    private List<QuizDto> quizQuestion ;
    private List<FieldDto> fields ;




}
