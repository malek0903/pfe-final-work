package org.tn.esprit.commons.dto;


import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto   implements Serializable {
    private Long idUser ;
    private String role;
    private String username;
    private String password;
    private String email;
    private Set<UserWorkflowAffectationDto> userWorkflowAffectationsDto ;

    public UserDto(Long idUser,String role, String username,String email) {
        this.idUser = idUser ;
        this.role = role;
        this.username = username;
        this.email = email;
       }
}
