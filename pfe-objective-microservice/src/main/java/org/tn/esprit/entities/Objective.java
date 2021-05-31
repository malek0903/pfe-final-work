package org.tn.esprit.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tn.esprit.commons.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "objectives")
@NoArgsConstructor
@AllArgsConstructor
public class Objective extends AbstractEntity {

    private String name;
    private Long idWorkflow;
    private Long idCategorie ;

}
