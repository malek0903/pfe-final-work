package org.tn.esprit.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.tn.esprit.commons.dto.FieldDto;
import org.tn.esprit.commons.dto.FormsResponseDto;
import org.tn.esprit.commons.dto.QuizDto;
import org.tn.esprit.commons.dto.ValueDto;
import org.tn.esprit.dao.FieldRepository;
import org.tn.esprit.dao.FormsResponseRepository;
import org.tn.esprit.dao.ValueRepository;
import org.tn.esprit.entities.Component;
import org.tn.esprit.entities.Field;
import org.tn.esprit.entities.Quiz;
import org.tn.esprit.entities.Value;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
@Transactional
public class FieldService {


    @Inject
    FieldRepository fieldRepository ;

    @Inject
    ValueRepository valueRepository ;

    @Inject
    FormsResponseRepository formsResponseRepository;



    public static FieldDto mapToDo(Field field){
        List<ValueDto> valuesDtos = field.getValues()
                .stream().map(FieldService::valueMapToDo).collect(Collectors.toList());
        List<FormsResponseDto> formsResponseDtos = field.getFormsResponses().
                stream().map(FormsResponseServiceImpl::mapToDo).collect(Collectors.toList());
        return new FieldDto(
                field.getId(),
                field.getName(),
                field.getType(),
                field.getIcon(),
                field.isToggle(),
                field.isRequired(),
                field.getRegex(),
                field.getErrorText(),
                field.getLabel(),
                field.getDescription(),
                field.getPlaceholder(),
                field.getClassName(),
                field.getSubtype(),
                field.isHandle(),
                field.getMin(),
                field.getMax(),
                field.isInline(),
                valuesDtos,
                formsResponseDtos

        );
    }
    public void saveField(FieldDto field, Component component){
        Field savedField = fieldRepository.save(new Field(
                field.getName(),
                field.getType(),
                field.getIcon(),
                field.isToggle(),
                field.isRequired(),
                field.getRegex(),
                field.getErrorText(),
                field.getLabel(),
                field.getDescription(),
                field.getPlaceholder(),
                field.getClassName(),
                field.getSubtype(),
                field.isHandle(),
                field.getMin(),
                field.getMax(),
                field.isInline(),
                component
        ));

        if(field.getValues().size() > 0){
        field.getValues().forEach(valueDto -> {
            valueRepository.save(new Value(
                    valueDto.getLabel(),
                    valueDto.getValue(),
                    savedField
            ));
        });
        }
    }

    public List<FieldDto> getFields(){
        return fieldRepository.findAll().stream().map(FieldService::mapToDo).collect(Collectors.toList());
    }

    public static ValueDto valueMapToDo(Value value){
        return new ValueDto(
                value.getLabel(),
                value.getValue()
        );
    }

    public void deleteAllFieldByComponent(List<Field> fields) {
        fields.forEach(field->{
            field.getFormsResponses().forEach(formsResponse -> formsResponseRepository.deleteAllByField(field));
        });
        fields.forEach(field->{
            field.getValues().forEach(value -> valueRepository.deleteAllByField(field));
        });
        fields.forEach(field -> {
            fieldRepository.deleteAllByComponent(field.getComponent());
        });
    }
    }
