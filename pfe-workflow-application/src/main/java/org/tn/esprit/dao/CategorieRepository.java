package org.tn.esprit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tn.esprit.entities.Categorie;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie ,Long> {

    Categorie findByCategorieName(String name);

    boolean existsByCategorieName(String name);
}
