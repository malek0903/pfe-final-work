package org.tn.esprit.ressource;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.tn.esprit.commons.dto.UserDto;
import org.tn.esprit.service.impl.TokenService;
import org.tn.esprit.service.impl.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/user")
//@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = " user", description = "All the user methods")
public class UserController {

    @Inject
    JsonWebToken jwt;
    @Inject
    TokenService tokenService;

    @Inject
    UserService userService ;

    @GET
    @Path("current/info")
    public JsonWebToken getCurrentUserInfo() {
        return jwt;
    }


    @GET()
    @Path("current/info-alternative")
    public Principal getCurrentUserInfoAlternative(@Context SecurityContext ctx) {
        return ctx.getUserPrincipal();
    }

    @GET
    @Path("current/info/claims")
    public Map<String, Object> getCurrentUserInfoClaims() {
        return jwt.getClaimNames()
                .stream()
                .map(name -> Map.entry(name, jwt.getClaim(name)))
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue())
                );
    }



    @POST
    @Path("access-token")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAccessToken(@RequestBody UserDto user)
            throws IOException, InterruptedException {
        System.out.println(user.toString());
        return tokenService.getAccessToken(user.getUsername(), user.getPassword());
    }


    @POST
    @Path("save-user-keycloak")
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveUserKeycloak(@RequestBody UserDto user)
            throws IOException, InterruptedException {

        System.out.println("hello");
         userService.saveUserKeycloak(user);
        System.out.println("hello");

    }






}
