package org.tn.esprit.service.impl;

import org.tn.esprit.commons.dto.FormsResponseDto;
import org.tn.esprit.dao.FieldRepository;
import org.tn.esprit.dao.FormsResponseRepository;
import org.tn.esprit.entities.Field;
import org.tn.esprit.entities.FormsResponse;
import org.tn.esprit.service.FormsResponseService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Objects;

@RequestScoped
public class FormsResponseServiceImpl implements FormsResponseService {

    @Inject
    FormsResponseRepository formsResponseRepository ;

    @Inject
    FieldRepository fieldRepository ;
    @Override
    public void createFormsResponsePerUser(FormsResponseDto formsResponseDto) {
        Field field = Objects.requireNonNull(fieldRepository.findById(formsResponseDto.getIdField())
                              .orElse(null));

        formsResponseRepository.save(new FormsResponse(
                formsResponseDto.getUsername(),
                formsResponseDto.getFormsResponseValue(),
                field
        ));
    }


    public static FormsResponseDto mapToDo(FormsResponse formsResponse){
        return new FormsResponseDto(
                formsResponse.getUsername(),
                formsResponse.getFormsResponseValue(),
                formsResponse.getField().getId()

        );
    }
}
