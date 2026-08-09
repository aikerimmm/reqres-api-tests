package qa.guru.reqres.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.reqres.models.CreateUserRequestDto;
import qa.guru.reqres.models.CreateUserResponseDto;
import qa.guru.reqres.models.UpdateUserRequestDto;
import qa.guru.reqres.models.UpdateUserResponseDto;
import qa.guru.reqres.models.UserDto;
import qa.guru.reqres.models.UserSearchResponseDto;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static qa.guru.reqres.specs.ReqresSpec.requestSpec;
import static qa.guru.reqres.specs.ReqresSpec.responseSpec;

@Epic("Reqres API")
@Feature("User Management")
@Owner("Aikerim")
public class ReqresApiTests {

    @Test
    @DisplayName("Create a new user")
    @Story("Create user")
    @Severity(CRITICAL)
    void createUserTest() {

        CreateUserRequestDto request =
                new CreateUserRequestDto(
                        "Aikerim",
                        "QA Automation Engineer"
                );

        CreateUserResponseDto response =
                given()
                        .spec(requestSpec)
                        .body(request)
                        .when()
                        .post("/users")
                        .then()
                        .spec(responseSpec)
                        .statusCode(201)
                        .extract()
                        .as(CreateUserResponseDto.class);

        assertEquals("Aikerim", response.getName());
        assertEquals("QA Automation Engineer", response.getJob());
        assertNotNull(response.getId());
        assertNotNull(response.getCreatedAt());
    }

    @Test
    @DisplayName("Get users list")
    @Story("Get users list")
    @Severity(NORMAL)
    void getUsersTest() {

        UserSearchResponseDto response =
                given()
                        .spec(requestSpec)
                        .queryParam("page", 2)
                        .when()
                        .get("/users")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract()
                        .as(UserSearchResponseDto.class);

        assertEquals(2, response.getPage());
        assertNotNull(response.getData());
        assertFalse(response.getData().isEmpty());
        assertNotNull(response.getData().get(0).getEmail());
    }

    @Test
    @DisplayName("Get single user")
    @Story("Get single user")
    @Severity(NORMAL)
    void getSingleUserTest() {

        UserDto response =
                given()
                        .spec(requestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract()
                        .jsonPath()
                        .getObject("data", UserDto.class);

        assertEquals(2, response.getId());
        assertNotNull(response.getEmail());
        assertNotNull(response.getFirstName());
        assertNotNull(response.getLastName());
    }

    @Test
    @DisplayName("Get non-existing user")
    @Story("Get non-existing user")
    @Severity(NORMAL)
    void userNotFoundTest() {

        given()
                .spec(requestSpec)
                .when()
                .get("/users/99999")
                .then()
                .spec(responseSpec)
                .statusCode(404);
    }

    @Test
    @DisplayName("Update user")
    @Story("Update user")
    @Severity(CRITICAL)
    void updateUserTest() {

        UpdateUserRequestDto request =
                new UpdateUserRequestDto(
                        "Aikerim",
                        "Senior QA Automation Engineer"
                );

        UpdateUserResponseDto response =
                given()
                        .spec(requestSpec)
                        .body(request)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract()
                        .as(UpdateUserResponseDto.class);

        assertEquals("Aikerim", response.getName());
        assertEquals("Senior QA Automation Engineer", response.getJob());
        assertNotNull(response.getUpdatedAt());
    }

    @Test
    @DisplayName("Delete user")
    @Story("Delete user")
    @Severity(CRITICAL)
    void deleteUserTest() {

        given()
                .spec(requestSpec)
                .when()
                .delete("/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(204);
    }
}