package org.tn.esprit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tn.esprit.entities.Component;
import org.tn.esprit.entities.Workflow;

@Repository
public interface ComponentRepository extends JpaRepository<Component,Long> {

    void deleteAllByWorkflow(Workflow workflow);
}
