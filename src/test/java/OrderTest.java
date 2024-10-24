import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.notNullValue;

import static io.restassured.RestAssured.given;

public class OrderTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/docs";

    }
    @Test
    public void createWithBlackColor() {
        Order order = new Order(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                "5",
                "2020-06-06",
                "Saske, come back to Konoha",
                new String[]{"BLACK"}
        );
        Response response = given()
                .header("Content-type", "application/json")
                .body(order)
                .post("/api/v1/orders");
        response.then().statusCode(201)
                .body("track", notNullValue());
    }

        @Test
        public void createWithGreyAndBlackColor () {
            Order order = new Order(
                    "Naruto",
                    "Uchiha",
                    "Konoha, 142 apt.",
                    "4",
                    "+7 800 355 35 35",
                    "5",
                    "2020-06-06",
                    "Saske, come back to Konoha",
                    new String[]{"GRAY", "BLACK"}
            );
            Response response = given()
                    .header("Content-type", "application/json")
                    .body(order)
                    .post("/api/v1/orders");
            response.then(). statusCode(201)
                    .body("track", notNullValue());
        }

    @Test
    public void createWithoutColor () {
        Order order = new Order(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                "5",
                "2020-06-06",
                "Saske, come back to Konoha",
                new String[]{}
        );
        Response response = given()
                .header("Content-type", "application/json")
                .body(order)
                .post("/api/v1/orders");
        response.then(). statusCode(201)
                .body("track", notNullValue());
    }

    }



