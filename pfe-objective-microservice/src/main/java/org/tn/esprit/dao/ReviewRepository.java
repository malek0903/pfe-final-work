package org.tn.esprit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tn.esprit.entities.Review;
@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    Review findByIdWorkflowAndUsername(Long idWorkflow, String userName);
    Boolean existsByIdWorkflow(Long workflowId);
}
