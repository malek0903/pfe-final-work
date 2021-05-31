package org.tn.esprit.ressource;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.logging.annotations.Pos;
import org.tn.esprit.commons.dto.CategorieDto;
import org.tn.esprit.entities.Categorie;
import org.tn.esprit.service.CategorieService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("categories")
@Produces(MediaType.APPLICATION_JSON)
public class CategorieController {

    @Inject
    CategorieService categorieService ;

    @GET
    public List<CategorieDto> getAllCatgories() {
        return categorieService.getAllCatgories();
    }


    @GET
    @Path("names")
    public List<String> getAllCatgoriesName() {
        return categorieService.getAllCategoriesName();
    }

    @GET
    @Path("{name}")
    public CategorieDto getCatgorieByName(@PathParam(value = "name") String name) {
        return categorieService.getCategoryDtoByName(name);
    }


    @GET
    @Path("/categories-id/{name}")
    public Long getCatgorieIdByName(@PathParam(value = "name") String name) {
        return categorieService.getCategorieIdByName(name);
    }
    @POST
    @Path("{categorieName}")
    public String saveCatgorie(@PathParam(value = "categorieName") String categorieName) {
        categorieService.saveCategorieIfNotExist(categorieName);
        return  "ok ! " ;
    }



}
