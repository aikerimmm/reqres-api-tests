package qa.guru.reqres.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import qa.guru.reqres.client.ReqresApiClient;

public class TestBase {

    protected final ReqresApiClient apiClient = new ReqresApiClient();

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = System.getenv()
                .getOrDefault("BASE_URL", "https://reqres.in");

        RestAssured.basePath = System.getenv()
                .getOrDefault("BASE_PATH", "/api");
    }
}