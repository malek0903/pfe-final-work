package org.tn.esprit.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.tn.esprit.entities.User;
import org.tn.esprit.entities.UserWorkflowAffectation;
import org.tn.esprit.entities.Workflow;

import java.util.List;

@Repository
public interface UserWorkflowAffectationRepository extends JpaRepository<UserWorkflowAffectation , Long> {

    UserWorkflowAffectation findByWorkflowAndUser(Workflow workflow, User user) ;
    UserWorkflowAffectation findByWorkflow(Workflow workflow) ;
    List<UserWorkflowAffectation> findAllByWorkflow(Workflow workflow);
    void deleteAllByWorkflow(Workflow workflow);

}
