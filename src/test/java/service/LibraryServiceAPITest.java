package service;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.AuthorRequest;
import model.AuthorResponse;
import model.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LibraryServiceAPITest {
    @Value("${my-service.url}")
    private String baseUrl;

    @Test
    public void testSaveNewAuthor_PositiveScenario() {
        AuthorRequest authorRequest = new AuthorRequest("Лев", "Толстой", "Николаевич");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(authorRequest)
                .when()
                .post(baseUrl + "/authors/save")
                .then()
                .statusCode(200)
                .extract()
                .response();

        AuthorResponse authorResponse = response.as(AuthorResponse.class);
        assertNotNull(authorResponse.getAuthorId());
    }

    @Test
    public void testSaveNewAuthor_NegativeScenario() {
        AuthorRequest authorRequest = new AuthorRequest("", "Толстой", "Николаевич");

        ErrorResponse errorResponse = given()
                .contentType(ContentType.JSON)
                .body(authorRequest)
                .when()
                .post(baseUrl + "/authors/save")
                .then()
                .statusCode(400)
                .extract()
                .as(ErrorResponse.class);

        assertEquals(1001, errorResponse.getErrorCode());
        assertEquals("Не передано имя автора", errorResponse.getErrorMessage());
    }

    @Configuration
    @PropertySource("classpath:application.yml")
    public static class AppConfig {
    }
}