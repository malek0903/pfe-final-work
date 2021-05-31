package org.tn.esprit.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tn.esprit.commons.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "quiz_options")
@NoArgsConstructor
@AllArgsConstructor
public class QuizOptions extends AbstractEntity {


    private int optionValue ;
    private String optionText ;
    @ManyToOne
    @JoinColumn(name = "idQuiz" ,referencedColumnName = "id" ,updatable=false)
    private Quiz quiz;
}
