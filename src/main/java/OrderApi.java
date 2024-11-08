import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    public Response createOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(BASE_URL + "/api/v1/orders");
    }

    public Response getOrder() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(BASE_URL + "/api/v1/orders");
    }
}
