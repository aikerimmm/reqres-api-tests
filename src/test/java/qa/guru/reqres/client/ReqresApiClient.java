package qa.guru.reqres.client;

import io.qameta.allure.Step;
import qa.guru.reqres.models.CreateUserRequestDto;
import qa.guru.reqres.models.CreateUserResponseDto;
import qa.guru.reqres.models.UpdateUserRequestDto;
import qa.guru.reqres.models.UpdateUserResponseDto;
import qa.guru.reqres.models.UserDto;
import qa.guru.reqres.models.UserSearchResponseDto;

import static io.restassured.RestAssured.given;
import static qa.guru.reqres.specs.ReqresSpec.requestSpec;
import static qa.guru.reqres.specs.ReqresSpec.responseSpec;

public class ReqresApiClient {

    @Step("Create user")
    public CreateUserResponseDto createUser(CreateUserRequestDto request) {
        return given()
                .spec(requestSpec())
                .body(request)
                .when()
                .post("/users")
                .then()
                .spec(responseSpec())
                .statusCode(201)
                .extract()
                .as(CreateUserResponseDto.class);
    }

    @Step("Get users from page {page}")
    public UserSearchResponseDto getUsers(int page) {
        return given()
                .spec(requestSpec())
                .queryParam("page", page)
                .when()
                .get("/users")
                .then()
                .spec(responseSpec())
                .statusCode(200)
                .extract()
                .as(UserSearchResponseDto.class);
    }

    @Step("Get user with id {userId}")
    public UserDto getUser(int userId) {
        return given()
                .spec(requestSpec())
                .when()
                .get("/users/" + userId)
                .then()
                .spec(responseSpec())
                .statusCode(200)
                .extract()
                .jsonPath()
                .getObject("data", UserDto.class);
    }

    @Step("Get non-existing user with id {userId}")
    public int getNonExistingUserStatusCode(int userId) {
        return given()
                .spec(requestSpec())
                .when()
                .get("/users/" + userId)
                .then()
                .spec(responseSpec())
                .extract()
                .statusCode();
    }

    @Step("Update user with id {userId}")
    public UpdateUserResponseDto updateUser(
            int userId,
            UpdateUserRequestDto request
    ) {
        return given()
                .spec(requestSpec())
                .body(request)
                .when()
                .put("/users/" + userId)
                .then()
                .spec(responseSpec())
                .statusCode(200)
                .extract()
                .as(UpdateUserResponseDto.class);
    }

    @Step("Delete user with id {userId}")
    public int deleteUser(int userId) {
        return given()
                .spec(requestSpec())
                .when()
                .delete("/users/" + userId)
                .then()
                .spec(responseSpec())
                .extract()
                .statusCode();
    }
}


