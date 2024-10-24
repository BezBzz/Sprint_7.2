import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {
    private CourierLogin courierLogin;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/docs";
//        courierLogin = new CourierLogin("ninja", "1234");
   }

    @Test
    public void courierLoginSuccess() {
        CourierLogin login = new CourierLogin("ninja", "1234");
        Response response = given()
                .header("Content-type", "application/json")
                .body(login)
                .post("/api/v1/courier/login");
        response.then().statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void cannotLoginWithIncorrectPassword() {
        CourierLogin login = new CourierLogin("ninja", "ghbdtn");
        given()
                .header("Content-type", "application/json")
                .body(login)
                .post("/api/v1/courier/login")
                .then().statusCode(400)
                .body("message", equalTo("неправильно указать логин или пароль"));
    }
//        @Test
//        public void cannotLoginWithoutLogin() {
//            CourierLogin login = new CourierLogin("ninja", "ghbdtn");
//            given()
//                    .header("Content-type", "application/json")
//                    .body(login)
//                    .post("/api/v1/courier/login")
//                    .then().statusCode(400)
//                    .body("message", equalTo("неправильно указан логин или пароль"));
//        }
    @Test
    public void cannotLoginAbsentUser() {
        CourierLogin login = new CourierLogin("ninja", "ghbdtn");
        given()
                .header("Content-type", "application/json")
                .body(login)
                .post("/api/v1/courier/login")
                .then().statusCode(404)
                .body("message", equalTo("пользователь не найден"));
    }
    }

