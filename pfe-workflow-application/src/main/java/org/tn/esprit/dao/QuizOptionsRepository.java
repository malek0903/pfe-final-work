package org.tn.esprit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tn.esprit.entities.Quiz;
import org.tn.esprit.entities.QuizOptions;

public interface QuizOptionsRepository extends JpaRepository<QuizOptions ,Long> {

    void deleteAllByQuiz(Quiz quiz);
}
