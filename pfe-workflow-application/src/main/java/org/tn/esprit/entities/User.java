package org.tn.esprit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.tn.esprit.commons.domain.AbstractEntity;
import org.tn.esprit.entities.enumeration.Role;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity {

    @Column(name = "username")
    private String username;

    @Column( name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "email")
    private String email ;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<UserWorkflowAffectation> userWorkflowAffectations ;

    public User(String username, Role role,String email) {
        this.username = username;
        this.role = role;
        this.email = email;
    }
}
