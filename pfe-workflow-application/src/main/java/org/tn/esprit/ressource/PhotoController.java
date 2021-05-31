package org.tn.esprit.ressource;

import org.tn.esprit.service.impl.UnsplashPhotoService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/photos")
public class PhotoController {

    @Inject
    UnsplashPhotoService photosService ;

    @GET
    @Path("{theme}")
    public List<String> getPhotosList(@PathParam(value = "theme") String theme){
        return photosService.getPhotos(theme);
    }
}
