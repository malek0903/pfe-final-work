package org.tn.esprit;


import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.DisabledOnNativeImage;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.tn.esprit.commons.utils.KeycloakRealmResource;
import org.tn.esprit.commons.utils.TestContainerResource;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

import static javax.ws.rs.core.Response.Status.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
@QuarkusTestResource(KeycloakRealmResource.class)
@DisabledOnNativeImage
public class ObjectiveResourceTest {

    static String ADMIN_BEARER_TOKEN;
    static String USER_BEARER_TOKEN;

    @BeforeAll
    static void init() {
        ADMIN_BEARER_TOKEN = System.getProperty("quatkus-admin-access-token");
        USER_BEARER_TOKEN = System.getProperty("quatkus-test-access-token");
    }

    @Test
    void testCreate() {

        var requestParams = new HashMap<>();
        requestParams.put("name", "Symfony");
        requestParams.put("idWorkflow", 2);
        requestParams.put("idCategorie", 2);

        given().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(requestParams)
                .post("/objectives")
                .then()
                .statusCode(UNAUTHORIZED.getStatusCode());
    }

    @Test
    void testFindAll() {
        given().when()
                .get("/objectives")
                .then()
                .statusCode(OK.getStatusCode())
                .body("size()", greaterThan(0))
                .body(containsString("name"))
                .body(containsString("idWorkflow"))
                .body(containsString("idCategorie"));
    }

    @Test
    void testFindById() {
        given().when()
                .get("/objectives/2")
                .then()
                .statusCode(OK.getStatusCode())
                .body(containsString("Angular Materiel"))
                .body(containsString("idWorkflow"))
                .body(containsString("idCategorie"));
    }


    @Test
    void testCreateWithAdminRole() {
        var count = given().when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_BEARER_TOKEN)
                .get("/objectives/count")
                .then()
                .extract()
                .body()
                .as(Long.class);

        var requestParams = new HashMap<>();
        requestParams.put("name", "Symfony");
        requestParams.put("idWorkflow", 2);
        requestParams.put("idCategorie", 2);


        given().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_BEARER_TOKEN)
                .body(requestParams)
                .post("/objectives")
                .then()
                .statusCode(OK.getStatusCode())
                .body(containsString("id"))
                .body(containsString("Symfony"));

        given().when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_BEARER_TOKEN)
                .get("/products/count")
                .then()
                .body(containsString(String.valueOf(count + 1)));
    }

    @Test
    void testFindByIdWithAdminRole() {
        given().when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_BEARER_TOKEN)
                .get("/objectives/2")
                .then()
                .statusCode(OK.getStatusCode())
                .body(containsString("Angular Materiel"))
                .body(containsString("idWorkflow"))
                .body(containsString("idCategorie"));
    }

    @Test
    void testCreateWithUserRole() {
        var count = given().when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
                .get("/objectives/count")
                .then()
                .extract()
                .body()
                .as(Long.class);

        var requestParams = new HashMap<>();
        requestParams.put("name", "Android conpetance");
        requestParams.put("idWorkflow", 3);
        requestParams.put("idCategorie", 2);

        given().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
                .body(requestParams)
                .post("/objectives")
                .then()
                .statusCode(FORBIDDEN.getStatusCode());
    }

    @Test
    void testFindAllWithUserRole() {
        given().when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
                .get("/objectives")
                .then()
                .statusCode(OK.getStatusCode())
                .body("size()", greaterThan(0))
                .body(containsString("name"))
                .body(containsString("idWorkflow"))
                .body(containsString("idCategorie"));
    }




}
