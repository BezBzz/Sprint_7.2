import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest {
    private CourierApi courierApi;
    private String login = "ninja";
    private String password = "1234";
    private String firstName = "saske";

    @Before
    public void setUp() {
        courierApi = new CourierApi();
    }

    @Test
    public void courierLoginSuccess() {
        Courier courier = new Courier();
        courierApi.createCourier(courier);
        Response response = courierApi.loginCourier(login, password);
        response.then()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
    }

    @Test
    public void cannotLoginWithIncorrectPassword() {
        Courier courier = new Courier();
        courierApi.createCourier(courier);
        Response response = courierApi.loginCourier(login,"132456");
        response.then()
                .statusCode(SC_CREATED)
                .body("message", equalTo("неправильно указать логин или пароль"));
    }


    @Test
    public void cannotLoginAbsentUser() {
        Courier courier = new Courier();
        courierApi.createCourier(courier);
        Response response = courierApi.loginCourier("login", password);
        response.then()
                .statusCode(SC_CREATED)
                .body("message", equalTo("пользователь не найден"));
    }

}

