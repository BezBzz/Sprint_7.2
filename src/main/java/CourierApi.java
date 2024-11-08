import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierApi {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    public Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .post(BASE_URL + "/api/v1/courier");
    }

    public Response deleteCourier(String courierId) {
        return given()
                .header("Content-type", "application/json")
                .delete(BASE_URL + "/api/v1/courier/" + courierId);
    }

    public Response loginCourier(CourierLogin courierLogin) {
        return given()
                .header("Content-type", "application/json")
                .body(courierLogin)
                .post(BASE_URL + "/api/v1/courier/login");
    }
}
