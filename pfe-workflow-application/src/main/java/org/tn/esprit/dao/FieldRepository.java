package org.tn.esprit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tn.esprit.entities.Component;
import org.tn.esprit.entities.Field;
@Repository
public interface FieldRepository extends JpaRepository<Field,Long> {
    void deleteAllByComponent(Component component);
}
