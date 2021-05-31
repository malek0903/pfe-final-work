package org.tn.esprit.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.tn.esprit.client.ObjectiveRestClient;
import org.tn.esprit.commons.dto.CategorieDto;
import org.tn.esprit.dao.CategorieRepository;
import org.tn.esprit.entities.Categorie;
import org.tn.esprit.service.CategorieService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@Transactional
public class CatgorieServiceImpl implements CategorieService {
    @Inject
    CategorieRepository categorieRepository ;

    @Inject
    @RestClient
    ObjectiveRestClient objectiveRestClient ;

    @Inject
    OxfordDictionaryService oxfordDictionaryService ;


    public static CategorieDto mapToDo(Categorie categorie){
        List<String> workflowsName = new ArrayList<>();
        categorie.getWorkflows().forEach(workflow -> {
            workflowsName.add(workflow.getWorkflowName());
        });
        return  new CategorieDto(categorie.getId(), categorie.getCategorieName(),workflowsName,categorie.getObjectives());
    }


    @Override
    public Categorie saveCategorieIfNotExist(String categorieName) {

          if(categorieRepository.existsByCategorieName(categorieName)){
              Categorie categorie = categorieRepository.findByCategorieName(categorieName);
              return categorie ;
          }else{
              if(findCatgoriesSynonyme(categorieName) != "") /// word matches with existing synonymes
                  return categorieRepository.findByCategorieName(findCatgoriesSynonyme(categorieName));
              else {
                  return  categorieRepository.save(new Categorie(
                          categorieName
                  ));
              }
          }
    }

    @Override
    public List<CategorieDto> getAllCatgories() {
            List<Categorie> allCategoriesWithTheirObjectives = new ArrayList<>();
            for (Categorie categorie : categorieRepository.findAll()){
                categorie.setObjectives(objectiveRestClient.getAllObjectivesByCategorieId(categorie.getId()));
                allCategoriesWithTheirObjectives.add(categorie);
            }
            return allCategoriesWithTheirObjectives.stream().map(CatgorieServiceImpl::mapToDo).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllCategoriesName() {
        return categorieRepository.findAll()
                .stream().map(Categorie::getCategorieName).collect(Collectors.toList());
    }

    @Override
    public CategorieDto getCategoryDtoByName(String categorieName) {
       Categorie categ =   categorieRepository.findByCategorieName(categorieName);
       try {
           categ.setObjectives(objectiveRestClient.getAllObjectivesByCategorieId(categ.getId()));
       }catch (Exception n){
           categ.setObjectives(new ArrayList<>());
       }
       return  mapToDo(categ);
    }

    @Override
    public Categorie getCategorieByName(String categorieName) {
        return categorieRepository.findByCategorieName(categorieName);
    }

    @Override
    public Long getCategorieIdByName(String categorieName) {
        try {
            return  categorieRepository.findByCategorieName(categorieName).getId();
        }catch (NullPointerException n){
            n.printStackTrace();
        }
        return 0L ;
    }


    public String findCatgoriesSynonyme(String categorieName){
        List<String> allCategoriesName = categorieRepository.findAll().stream()
                .map(Categorie::getCategorieName).collect(Collectors.toList());
        for (String categ : allCategoriesName){
           List<String> allSynonymsByCatgorie =  oxfordDictionaryService.getWordSynonyme(categ);
           for (String match : allSynonymsByCatgorie){
               if (categorieName.equals(match))
                   return categ;
           }
        }
        return "" ;
    }

}
