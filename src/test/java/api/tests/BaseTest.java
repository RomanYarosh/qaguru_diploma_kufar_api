package api.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import utils.PropertyReader;

public class BaseTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = PropertyReader.getProperty("api.base.url");
    }
}