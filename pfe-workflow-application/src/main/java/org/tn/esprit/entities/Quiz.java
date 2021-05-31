package org.tn.esprit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tn.esprit.commons.domain.AbstractEntity;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "quizzes")
@NoArgsConstructor
@AllArgsConstructor
public class Quiz extends AbstractEntity {


    private Long questionId ;
    private String questionText ;
    private int answer ;
    private String explanation;
    private int selectedOption ;


    @OneToMany(mappedBy = "quiz")
    private List<QuizOptions> quizOptions ;

    @ManyToOne
    @JoinColumn(name = "idComponent" ,referencedColumnName = "id" ,updatable=false)
    private Component component;


    public Quiz(Long questionId,String questionText, int answer, String explanation, int selectedOption, Component component) {
    this.questionId = questionId ;
        this.questionText = questionText;
        this.answer = answer;
        this.explanation = explanation;
        this.selectedOption = selectedOption;
        this.component = component;
    }
}
