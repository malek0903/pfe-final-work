package org.tn.esprit.ressource;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.tn.esprit.commons.dto.FormsResponseDto;
import org.tn.esprit.service.FormsResponseService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("forms-response")
@Consumes(MediaType.APPLICATION_JSON)
public class FormsResponseController {

    @Inject
    FormsResponseService formsResponseService ;

    @POST
    public void saveFormsResponse(@RequestBody FormsResponseDto formsResponseDto){
        formsResponseService.createFormsResponsePerUser(formsResponseDto);
    }
}
