package qa.guru.reqres.specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;

public class ReqresSpec {

    private static final String API_KEY = getApiKey();

    public static RequestSpecification requestSpec =
            new RequestSpecBuilder()
                    .setBaseUri("https://reqres.in")
                    .setBasePath("/api")
                    .setContentType(ContentType.JSON)
                    .addHeader("x-api-key", API_KEY)
                    .log(ALL)
                    .addFilter(
                            new AllureRestAssured()
                                    .setRequestTemplate("request.ftl")
                                    .setResponseTemplate("response.ftl")
                    )
                    .build();

    public static ResponseSpecification responseSpec =
            new ResponseSpecBuilder()
                    .log(ALL)
                    .build();

    private static String getApiKey() {
        String apiKey = System.getenv("REQRES_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "REQRES_API_KEY environment variable is not set"
            );
        }

        return apiKey;
    }
}