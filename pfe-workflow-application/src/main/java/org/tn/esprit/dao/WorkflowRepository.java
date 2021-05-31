package org.tn.esprit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tn.esprit.entities.Workflow;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, Long> {

    Workflow findWorkflowByWorkflowName(String name);
}
