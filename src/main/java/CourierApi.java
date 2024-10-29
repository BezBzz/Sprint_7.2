import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierApi {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/docs/api/v1/courier";

    public Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .post(BASE_URL);
    }

    public Response deleteCourier(int courierId) {
        return given()
                .header("Content-type", "application/json")
                .delete(BASE_URL + "/idCourier");
    }

    public Response loginCourier(String courierLogin, String courierPassword) {
        return given()
                .header("Content-type", "application/json")
                .body(courierLogin)
                .post(BASE_URL + "/login");
    }
}
