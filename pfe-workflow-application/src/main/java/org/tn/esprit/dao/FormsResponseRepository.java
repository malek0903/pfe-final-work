package org.tn.esprit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tn.esprit.entities.Field;
import org.tn.esprit.entities.FormsResponse;
@Repository
public interface FormsResponseRepository extends JpaRepository<FormsResponse,Long> {
    void deleteAllByField(Field field);
}
