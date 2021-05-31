package org.tn.esprit.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldDto {
    private Long id ;
    private String name ;
    private  String type ;
    private String icon;
    private boolean toggle ;
    private boolean required ;
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
    private List<ValueDto> values ;
    private List<FormsResponseDto> formsResponseDtos;
}
