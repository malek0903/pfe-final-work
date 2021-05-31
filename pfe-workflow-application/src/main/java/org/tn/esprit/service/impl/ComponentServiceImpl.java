package org.tn.esprit.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.tn.esprit.commons.dto.FieldDto;
import org.tn.esprit.commons.dto.QuizDto;
import org.tn.esprit.dao.ComponentRepository;
import org.tn.esprit.commons.dto.ComponentDto;
import org.tn.esprit.entities.Component;
import org.tn.esprit.entities.Quiz;
import org.tn.esprit.entities.Workflow;
import org.tn.esprit.entities.enumeration.Type;
import org.tn.esprit.service.ComponentService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@Transactional
public class ComponentServiceImpl implements ComponentService {

    @Inject
    ComponentRepository componentRepository;

    @Inject
    QuizService quizService;


    @Inject
    FieldService fieldService;

    public static ComponentDto mapToDto(Component compoenet) {
        List<QuizDto> quizDtos = compoenet.getQuizzes()
                .stream().map(QuizService::mapToDo).collect(Collectors.toList());
        List<FieldDto> fieldsDtos = compoenet.getFields()
                .stream().map(FieldService::mapToDo).collect(Collectors.toList())
                .stream().sorted(Comparator.comparingLong(FieldDto::getId)).collect(Collectors.toList());

        return new ComponentDto(
                compoenet.getId(),
                compoenet.getName(),
                compoenet.getSourceId(),
                compoenet.getDestinationId(),
                compoenet.getLink(),
                compoenet.getType().name(),
                compoenet.getFile(),
                compoenet.getFileType(),
                compoenet.getStep(),
                compoenet.getTextColor(),
                compoenet.getBackgroundColor(),
                compoenet.getBold(),
                compoenet.getItalic(),
                compoenet.getUnderline(),
                compoenet.getFontSize(),
                compoenet.getFontFamily(),
                compoenet.getWorkflow().getId(),
                compoenet.getQuizScore(),
                compoenet.getTimePerQuestion(),
                quizDtos,
                fieldsDtos
        );
    }

    @Transactional
    public void updateOrAddOrDeleteComponents(List<ComponentDto> componentsDto, Workflow workflow) {
        //update component
        workflow.getComponents().forEach(component -> {
            quizService.deleteAllQuizByComponent(component.getQuizzes());
        });
        workflow.getComponents().forEach(component -> {
            fieldService.deleteAllFieldByComponent(component.getFields());
        });
        componentRepository.deleteAllByWorkflow(workflow);
        prepareComponentToBeSaved(componentsDto, workflow);
    }


    public void prepareComponentToBeSaved(List<ComponentDto> compots, Workflow workflow) {
        String compoId = "";
        int stepNumber = 1;
        for (ComponentDto compo : compots) {
            if (compo.getDestinationId() == null) {
                compoId = compo.getSourceId();
                compo.setStep(stepNumber);
                stepNumber++;
                saveCompo(compo, workflow);
            }
        }
        do {
            for (ComponentDto compo : compots) {
                try {
                    if (compo.getDestinationId() != null && compo.getDestinationId().equals(compoId)) {
                        compo.setStep(stepNumber);
                        stepNumber++;
                        compoId = compo.getSourceId();
                        saveCompo(compo, workflow);
                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        } while (exit(compoId, compots));
    }

    public void saveCompo(ComponentDto compo, Workflow workflow) {

        Component component = componentRepository.save(new Component(
                compo.getName(),
                compo.getSourceId(),
                compo.getDestinationId(),
                compo.getLink(),
                compo.getFile(),
                compo.getFileType(),
                Type.valueOf(compo.getType()),
                compo.getStep(),
                compo.getTextColor(),
                compo.getBackgroundColor(),
                compo.getBold(),
                compo.getItalic(),
                compo.getUnderline(),
                compo.getFontSize(),
                compo.getFontFamily(),
                workflow,
                compo.getQuizScore(),
                compo.getTimePerQuestion()
        ));
        if (compo.getQuizQuestion().size() > 0) {
            compo.getQuizQuestion().forEach(quiz -> {
                quizService.saveQuiz(quiz, component);
            });
        }

        if (compo.getFields().size() > 0) {
            compo.getFields().forEach(field -> {
                fieldService.saveField(field, component);
            });
        }

    }

    public boolean exit(String compoId, List<ComponentDto> compots) {
        int repeated = 0;
        for (ComponentDto compo : compots) {
            if (compo.getSourceId().equals(compoId))
                repeated++;
        }
        if (repeated > 1)
            return true;
        else return false;
    }

    @Override
    public void saveComponent(ComponentDto compoenet) {
    }
}
