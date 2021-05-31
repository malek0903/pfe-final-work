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

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
@QuarkusTestResource(KeycloakRealmResource.class)
@DisabledOnNativeImage
public class ReviewResourceTest {

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
        requestParams.put("raiting", 5);
        requestParams.put("feedback", "good workflow");
        requestParams.put("idWorkflow", 2);
        requestParams.put("username", "user1");

        given().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(requestParams)
                .post("/reviews")
                .then()
                .statusCode(UNAUTHORIZED.getStatusCode());
    }

    @Test
    void testFindAll() {
        given().when()
                .get("/reviews")
                .then()
                .statusCode(OK.getStatusCode())
                .body("size()", greaterThan(0))
                .body(containsString("id"))
                .body(containsString("raiting"))
                .body(containsString("feedback"))
                .body(containsString("idWorkflow"))
                .body(containsString("idCategorie"));
    }

    @Test
    void testCreateWithUserRole() {
        var count = given().when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_BEARER_TOKEN)
                .get("/reviews/count")
                .then()
                .extract()
                .body()
                .as(Long.class);

        var requestParams = new HashMap<>();
        requestParams.put("raiting", 5);
        requestParams.put("feedback", "good workflow");
        requestParams.put("idWorkflow", 2);
        requestParams.put("username", "user1");

        given().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
                .body(requestParams)
                .post("/reviews")
                .then()
                .statusCode(OK.getStatusCode())
                .body(containsString("id"))
                .body(containsString("good workflow"));

        given().when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
                .get("/reviews/count")
                .then()
                .body(containsString(String.valueOf(count + 1)));
    }
}
