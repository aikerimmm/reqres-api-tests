package qa.guru.reqres.specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;

public class ReqresSpec {

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("x-api-key", getApiKey())
                .log(ALL)
                .addFilter(
                        new AllureRestAssured()
                                .setRequestTemplate("request.ftl")
                                .setResponseTemplate("response.ftl")
                )
                .build();
    }

    public static ResponseSpecification responseSpec() {
        return new ResponseSpecBuilder()
                .log(ALL)
                .build();
    }

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