package org.tn.esprit.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tn.esprit.commons.domain.AbstractEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "forms_response")
@NoArgsConstructor
@AllArgsConstructor
public class FormsResponse extends AbstractEntity {

    private String username;
    @Column(name = "forms_response_value")
    private  String formsResponseValue;

    @ManyToOne
    @JoinColumn(name = "idField" ,referencedColumnName = "id" ,updatable=false)
    private Field field;
}
