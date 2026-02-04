package api.specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.PropertyReader;

public class Specs {
    private static final AllureRestAssured FILTER = new AllureRestAssured()
            .setRequestTemplate("request.ftl")
            .setResponseTemplate("response.ftl");

    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(PropertyReader.getProperty("api.base.url"))
            .addFilter(FILTER)
            .log(LogDetail.ALL)
            .build();

    public static ResponseSpecification responseSpecOK200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();
}