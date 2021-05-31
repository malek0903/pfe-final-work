package org.tn.esprit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tn.esprit.entities.Component;
import org.tn.esprit.entities.Quiz;

public interface QuizRepository  extends JpaRepository<Quiz,Long> {
    void deleteAllByComponent(Component component);
}
