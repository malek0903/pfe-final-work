package org.tn.esprit.entities;

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
@Table(name = "fields")
@NoArgsConstructor
@AllArgsConstructor
public class Field  extends AbstractEntity {
    private String name ;
    private  String type ;
    private String icon;
    private boolean toggle ;
    private boolean required ;
    @Column(name="regex", length=512)
    private String regex;
    private String  errorText ;
    private String  label;
    private String   description;
    private String placeholder;
    private String className;
    private String subtype;
    private boolean handle;
    private Long min;
    private Long  max;
    private boolean inline;


    @OneToMany(mappedBy = "field")
    private List<FormsResponse> formsResponses;


    @ManyToOne
    @JoinColumn(name = "idComponent" ,referencedColumnName = "id" ,updatable=false)
    private Component component;

    @OneToMany(mappedBy = "field")
    private List<Value> values ;

    public Field(String name, String type, String icon, boolean toggle, boolean required, String regex, String errorText, String label, String description, String placeholder, String className, String subtype, boolean handle, Long min, Long max, boolean inline, Component component) {
        this.name = name;
        this.type = type;
        this.icon = icon;
        this.toggle = toggle;
        this.required = required;
        this.regex = regex;
        this.errorText = errorText;
        this.label = label;
        this.description = description;
        this.placeholder = placeholder;
        this.className = className;
        this.subtype = subtype;
        this.handle = handle;
        this.min = min;
        this.max = max;
        this.inline = inline;
        this.component = component;
    }
}
