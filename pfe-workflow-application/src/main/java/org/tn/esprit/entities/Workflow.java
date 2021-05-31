package org.tn.esprit.entities;

import lombok.*;
import org.tn.esprit.commons.domain.AbstractEntity;
import org.tn.esprit.entities.enumeration.Status;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "workflows")
@NoArgsConstructor
public class Workflow extends AbstractEntity {


    @Column(name = "workflow_name" ,unique = true)
    private String workflowName;

    @Enumerated(EnumType.STRING)
    private Status status ;

    private boolean prototype;
    @Column(name = "workflow_prototype_status")
    private String workflowPrototypeStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private Categorie categorie ;
    @Column(name = "workflow_creator_username")
    private String workflowCreatorUsername;
    @OneToMany(mappedBy = "workflow", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Component> components;

    @OneToMany(mappedBy = "workflow")
    private Set<UserWorkflowAffectation> userWorkflowAffectations;
    private boolean deleted;
    private boolean updated;

    public Workflow(String workflowName,boolean prototype, String workflowPrototypeStatus,Categorie categorie,Status status,String workflowCreatorUsername) {
        this.workflowName = workflowName;
        this.prototype = prototype;
        this.workflowPrototypeStatus = workflowPrototypeStatus;
        this.categorie = categorie;
        this.status = status ;
        this.workflowCreatorUsername = workflowCreatorUsername;

    }
}
