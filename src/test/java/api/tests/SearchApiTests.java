package api.tests;

import api.models.AdModel;
import api.models.SearchResponse;
import api.specs.Specs;
import org.junit.jupiter.api.*;
import utils.PropertyReader;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SearchApiTests extends BaseTest {

    @Test
    @Order(1)
    @DisplayName("API 1: Поиск товаров по названию")
    void successSearchTest() {
        String query = PropertyReader.getProperty("search.query");

        SearchResponse response = given()
                .spec(Specs.requestSpec)
                .queryParam("query", query)
                .queryParam("lang", "ru")
                .queryParam("size", "10")
        .when()
                .get(PropertyReader.getProperty("api.search.path"))
        .then()
                .spec(Specs.responseSpecOK200)
                .extract().as(SearchResponse.class); // Десериализация JSON в объект Java

        assertNotNull(response.getAds());
        assertFalse(response.getAds().isEmpty());
    }

    @Test
    @Order(2)
    @DisplayName("API 2: Проверка региона")
    void regionFilterApiTest() {
        SearchResponse response = given()
                .spec(Specs.requestSpec)
                .queryParam("query", PropertyReader.getProperty("search.query"))
                .queryParam("rgn", "7")
                .queryParam("lang", "ru")
        .when()
                .get(PropertyReader.getProperty("api.search.path"))
        .then()
                .spec(Specs.responseSpecOK200)
                .extract().as(SearchResponse.class);

        String actualRegion = response.getAds().get(0).getAdParameters().stream()
                .filter(p -> p.getP().equals("region"))
                .findFirst()
                .map(p -> String.valueOf(p.getV())) // Используем String.valueOf для безопасности
                .orElse(null);

        assertEquals("7", actualRegion);
    }

    @Test
    @Order(3)
    @DisplayName("API 3: Сортировка по цене (убывание)")
    void sortingApiTest() {
        SearchResponse response = given()
                .spec(Specs.requestSpec)
                .queryParam("query", PropertyReader.getProperty("search.query"))
                .queryParam("sort", "prc.d")
                .queryParam("lang", "ru")
        .when()
                .get(PropertyReader.getProperty("api.search.path"))
        .then()
                .spec(Specs.responseSpecOK200)
                .extract().as(SearchResponse.class);

        assertNotNull(response.getAds().get(0).getPriceByn());
    }

    @Test
    @Order(4)
    @DisplayName("API 4: Фильтрация по категории (Телефоны)")
    void categoryFilterApiTest() {
        String category = PropertyReader.getProperty("phone.category");

        SearchResponse response = given()
                .spec(Specs.requestSpec)
                .queryParam("query", "iphone")
                .queryParam("cat", category)
                .queryParam("lang", "ru")
        .when()
                .get(PropertyReader.getProperty("api.search.path"))
        .then()
                .spec(Specs.responseSpecOK200)
                .extract().as(SearchResponse.class);

        assertEquals(category, response.getAds().get(0).getCategory());
    }

    @Test
    @Order(5)
    @DisplayName("API 5: Проверка пагинации")
    void paginationSizeTest() {
        int size = 5;
        SearchResponse response = given()
                .spec(Specs.requestSpec)
                .queryParam("query", "iphone")
                .queryParam("lang", "ru")
                .queryParam("size", size)
        .when()
                .get(PropertyReader.getProperty("api.search.path"))
        .then()
                .spec(Specs.responseSpecOK200)
                .extract().as(SearchResponse.class);

        assertEquals(size, response.getAds().size());
    }
}