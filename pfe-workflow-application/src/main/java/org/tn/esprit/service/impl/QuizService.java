package org.tn.esprit.service.impl;

import org.tn.esprit.commons.dto.ComponentDto;
import org.tn.esprit.commons.dto.QuizDto;
import org.tn.esprit.commons.dto.QuizOptionDto;
import org.tn.esprit.dao.QuizOptionsRepository;
import org.tn.esprit.dao.QuizRepository;
import org.tn.esprit.entities.Component;
import org.tn.esprit.entities.Quiz;
import org.tn.esprit.entities.QuizOptions;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class QuizService {

    @Inject
    QuizRepository quizRepository ;

    @Inject
    QuizOptionsRepository quizOptionsRepository ;


    public static QuizOptionDto quizOptionMapToDo(QuizOptions quiz){
        return new QuizOptionDto(
                quiz.getId(),
                quiz.getOptionValue(),
                quiz.getOptionText()
        );
    }

    public static QuizDto mapToDo(Quiz quiz){
        List<QuizOptionDto> quizOptions = quiz.getQuizOptions()
                .stream().map(QuizService::quizOptionMapToDo).collect(Collectors.toList());
        return new QuizDto(
                quiz.getId(),
                quiz.getQuestionId(),
                quiz.getQuestionText(),
                quiz.getAnswer(),
                quiz.getExplanation(),
                quiz.getSelectedOption(),
                quizOptions
        );
    }

    public void saveQuiz(QuizDto quizDto, Component component){

        Quiz quiz = quizRepository.save(new Quiz(
                quizDto.getQuestionId(),
                quizDto.getQuestionText(),
                quizDto.getAnswer(),
                quizDto.getExplanation(),
                quizDto.getSelectedOption(),
                component
        ));

        quizDto.getOptions().forEach(quizOptionDto -> {
            saveQuizOptions(quizOptionDto,quiz);
        });

    }
    @Transactional
    public void deleteAllQuizByComponent(List<Quiz> quizzes){
        quizzes.forEach(quiz->{
            quiz.getQuizOptions().forEach(quizOptions -> quizOptionsRepository.deleteAllByQuiz(quiz));
        });
        quizzes.forEach(quiz -> {
            quizRepository.deleteAllByComponent(quiz.getComponent());
        });
    }



    public void saveQuizOptions(QuizOptionDto quizOptionDto, Quiz quiz){
        quizOptionsRepository.save(new QuizOptions(
                quizOptionDto.getOptionValue(),
                quizOptionDto.getOptionText(),
                quiz
        ));
    }

    public void updateQuiz(QuizDto quizDto, Component component){
       quizRepository.findById(quizDto.getId()).ifPresent(quiz -> {
            quiz.setQuestionId(quizDto.getQuestionId());
            quiz.setQuestionText(quizDto.getQuestionText());
            quiz.setAnswer(quizDto.getAnswer());
            quiz.setAnswer(quiz.getAnswer());
            quiz.setExplanation(quizDto.getExplanation());
            quiz.setComponent(component);
            quizDto.getOptions().forEach(quizOptionDto -> {
                if (quizOptionDto.getId() != null) updateQuizQuestion(quizOptionDto ,quiz);
           });
       });

    }

    public void updateQuizQuestion(QuizOptionDto quizOptionDto, Quiz quiz){
        quizOptionsRepository.findById(quizOptionDto.getId()).ifPresent(quizOptions -> {
            quizOptions.setOptionText(quizOptionDto.getOptionText());
            quizOptions.setOptionValue(quizOptionDto.getOptionValue());
            quizOptions.setQuiz(quiz);
        });
    }

}
