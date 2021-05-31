package org.tn.esprit.service;

import org.tn.esprit.commons.dto.CategorieDto;
import org.tn.esprit.entities.Categorie;

import java.util.List;

public interface CategorieService {

    Categorie saveCategorieIfNotExist(String categorieName);

    List<CategorieDto> getAllCatgories();

    List<String> getAllCategoriesName();

    CategorieDto getCategoryDtoByName(String categorieName);
    Categorie getCategorieByName(String categorieName);

    Long  getCategorieIdByName(String categorieName);

}
