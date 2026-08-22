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

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Epic("Reqres API")
@Feature("User Management")
@Owner("Aikerim")
public class ReqresApiTests extends TestBase {

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
                apiClient.createUser(request);

        step("Check created user data", () -> {
            assertEquals("Aikerim", response.getName());
            assertEquals("QA Automation Engineer", response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }

    @Test
    @DisplayName("Get users list")
    @Story("Get users list")
    @Severity(NORMAL)
    void getUsersTest() {

        UserSearchResponseDto response =
                apiClient.getUsers(2);

        step("Check users list", () -> {
            assertEquals(2, response.getPage());
            assertNotNull(response.getData());
            assertFalse(response.getData().isEmpty());
            assertNotNull(response.getData().get(0).getEmail());
        });
    }

    @Test
    @DisplayName("Get single user")
    @Story("Get single user")
    @Severity(NORMAL)
    void getSingleUserTest() {

        UserDto response =
                apiClient.getUser(2);

        step("Check user data", () -> {
            assertEquals(2, response.getId());
            assertNotNull(response.getEmail());
            assertNotNull(response.getFirstName());
            assertNotNull(response.getLastName());
        });
    }

    @Test
    @DisplayName("Get non-existing user")
    @Story("Get non-existing user")
    @Severity(NORMAL)
    void userNotFoundTest() {

        int statusCode =
                apiClient.getNonExistingUserStatusCode(99999);

        step("Check that user is not found", () ->
                assertEquals(404, statusCode)
        );
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
                apiClient.updateUser(2, request);

        step("Check updated user data", () -> {
            assertEquals("Aikerim", response.getName());
            assertEquals("Senior QA Automation Engineer", response.getJob());
            assertNotNull(response.getUpdatedAt());
        });
    }

    @Test
    @DisplayName("Delete user")
    @Story("Delete user")
    @Severity(CRITICAL)
    void deleteUserTest() {

        int statusCode =
                apiClient.deleteUser(2);

        step("Check that user is deleted", () ->
                assertEquals(204, statusCode)
        );
    }
}