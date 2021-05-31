package org.tn.esprit.service.impl;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.tn.esprit.dao.UserRepository;
import org.tn.esprit.dao.UserWorkflowAffectationRepository;
import org.tn.esprit.commons.dto.UserDto;
import org.tn.esprit.entities.User;
import org.tn.esprit.entities.UserWorkflowAffectation;
import org.tn.esprit.entities.Workflow;
import org.tn.esprit.entities.enumeration.Role;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Set;

@Slf4j
@ApplicationScoped
@Transactional
public class UserService {


    @Inject
    UserRepository userRepository;

    @Inject
    Mailer mailer;

    @Inject
    UserWorkflowAffectationRepository userWorkflowAffectationRepository;

    @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "undefined")
    Provider<String> jwtIssuerUrlProvider;
    @ConfigProperty(name = "keycloak.credentials.client-id", defaultValue = "undefined")
    Provider<String> clientIdProvider;

    @Inject
    TokenService tokenService;

    public static UserDto mapToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getRole().name(),
                user.getUsername(),
                user.getEmail()
        );
    }


    public void saveUserKeycloak(UserDto user) throws IOException, InterruptedException {
        Keycloak keycloak = KeycloakBuilder.builder() //
                .serverUrl("http://localhost:9080/auth") //
                .realm("pfe-realm") //
                .grantType(OAuth2Constants.PASSWORD) //
                .clientId("workflow-ms-client") //
                .authorization(tokenService.getAccessToken("malek", "123"))
                .username("malek") //
                .password("123") //
                .build();
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setFirstName(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        RealmResource realmResource = keycloak.realm("pfe-realm");
        UsersResource usersRessource = realmResource.users();


        // Create user (requires manage-users role)
        Response response = usersRessource.create(userRepresentation);
        System.out.printf("Repsonse: %s %s%n", response.getStatus(), response.getStatusInfo());
        System.out.println(response.getLocation());
        String userId = CreatedResponseUtil.getCreatedId(response);

        System.out.printf("User created with userId: %s%n", userId);

        // Define password credential
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(user.getPassword());

        UserResource userResource = usersRessource.get(userId);

        // Set password credential
        userResource.resetPassword(passwordCred);

    }


    public void saveUsers(Set<UserDto> users, Workflow workflow) {
        users.forEach(item -> {
            if (userRepository.existsUserByUsername(item.getUsername())) {
                User user = userRepository.findUserByUsername(item.getUsername());
                userWorkflowAffectationRepository.save(new UserWorkflowAffectation(
                        user,
                        workflow,
                        0
                ));
            } else {
                System.out.println(item.toString());
                if (item.getUsername().equals("undefined")) {
                    sendMailToNewUserAndSaveItInKeycloak(item);
                }
                User user = userRepository.save(new User(
                        item.getUsername().equals("undefined") ? item.getEmail().split("@", 2)[0] : item.getUsername(),
                        Role.valueOf(item.getRole()),
                        item.getEmail()
                ));
                userWorkflowAffectationRepository.save(new UserWorkflowAffectation(
                        user,
                        workflow,
                        0
                ));
            }
        });
    }


    public void updateOrDeleteUserIfExist(Set<UserDto> users, Workflow workflow) {
             userWorkflowAffectationRepository.deleteAllByWorkflow(workflow);
            saveUsers(users,workflow);
    }

    public void sendMailToNewUserAndSaveItInKeycloak(UserDto userDto) {
        String giveRandomPasswordToUser = generateRandomPassword(8);
        String username = userDto.getEmail().split("@", 2)[0];
        String body = "<strong>Welcome to Bubble ! </strong>" + "\n" +
                " <h1>This is the Link to access to your Workflow </h1>" + "\n" +
                "<h1>http://localhost:4200</h1>" + "\n" +
                "<p> Login :<i> " + username + "</i> </p>" + "\n" +
                "<p><b> Password: </b> " + giveRandomPasswordToUser + "</p>" +
                "<p>Regards</p>" ;
              //  "<img src=\"cid:my-image@quarkus.io\"/> width=\"200\" height=\"40\" ";
        mailer.send(Mail.withHtml(userDto.getEmail(), "An email in HTML", body));
       /*         .addInlineAttachment("acretio.jpeg",
                        new File("/home/malek/Documents/pfe-application/pfe-root-final/pfe-workflow-application/src/main/resources/META-INF/resources/acretio.jpeg"),
                        "image/jpeg", "<my-image@quarkus.io>"));
       */ //save User keycloak
        try {
            UserDto userToBeSentToKeycloak = new UserDto();
            userToBeSentToKeycloak.setEmail(userDto.getEmail());
            userToBeSentToKeycloak.setUsername(username);
            userToBeSentToKeycloak.setPassword(giveRandomPasswordToUser);
            saveUserKeycloak(userToBeSentToKeycloak);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Function to generate random alpha-numeric password of specific length
    public static String generateRandomPassword(int len) {
        // ASCII range - alphanumeric (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789*$#/%?!";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // each iteration of loop choose a character randomly from the given ASCII range
        // and append it to StringBuilder instance

        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }

}
