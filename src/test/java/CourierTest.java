import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.BooleanUtils.and;
import static org.hamcrest.Matchers.equalTo;

public class CourierTest {
    private Courier courier;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/docs";
        courier = new Courier("ninja", "1234","saske");
    }

    @Test
    public void createCourierDone() {
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
        response.then().assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @Test
    public void createCourierRetry() {
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
        response.then().assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(201);
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier")
                .then().statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));

    }
    @Test
    public void cannotCreateCourierWithoutLogin() {
        Courier courier = new Courier(null, "1234","saske");
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("//api/v1/courier")
                .then().statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}
