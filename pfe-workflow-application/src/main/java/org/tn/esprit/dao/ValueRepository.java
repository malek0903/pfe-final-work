package org.tn.esprit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tn.esprit.entities.Field;
import org.tn.esprit.entities.Value;

public interface ValueRepository extends JpaRepository<Value,Long> {
    void deleteAllByField(Field field);
}
