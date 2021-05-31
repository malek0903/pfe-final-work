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
@Table(name = "users_workflows")
@AllArgsConstructor
@NoArgsConstructor
public class UserWorkflowAffectation extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "idUser" ,referencedColumnName = "id",updatable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "idWorkflow" ,referencedColumnName = "id" ,updatable=false)
    private Workflow workflow;

    private int userComponentStep ;

}
