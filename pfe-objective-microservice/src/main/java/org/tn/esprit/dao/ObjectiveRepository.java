package org.tn.esprit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tn.esprit.entities.Objective;
@Repository
public interface ObjectiveRepository extends JpaRepository<Objective,Long> {

    boolean existsByName(String name);
}
