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
@Table(name = "values")
@NoArgsConstructor
@AllArgsConstructor
public class Value extends AbstractEntity {

    private String label;
    private String value ;
    @ManyToOne
    @JoinColumn(name = "idField" ,referencedColumnName = "id" ,updatable=false)
    private Field field;
}
