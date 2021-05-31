package org.tn.esprit.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tn.esprit.commons.domain.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories")
@NoArgsConstructor
public class Categorie extends AbstractEntity {
    @Column(name = "categorie_name")
    private String categorieName ;

    @OneToMany(mappedBy = "categorie", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Workflow> workflows ;

    @Transient
    private List<String> objectives ;

    public Categorie(String categorieName) {
        this.categorieName = categorieName;
    }
}
