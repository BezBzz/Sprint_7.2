import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {
    private final Faker faker = new Faker();
    private CourierApi courierApi;
    private String login = faker.name().username();
    private String password = faker.internet().password(3, 6);
    private String firstName = faker.name().firstName();
    private String courierId;

    @Before
    public void setUp() {
        courierApi = new CourierApi();
    }

    @After
    public void tearDown() {
        Response response = courierApi.deleteCourier(courierId);
        response.then().statusCode(SC_OK).body("ok", equalTo(true));
        System.out.println(response.body().asString());
    }

    @Test
    public void courierLoginSuccess() {
        Courier courier = new Courier(login, password, firstName);
        courierApi.createCourier(courier);
        Response response = courierApi.loginCourier(new CourierLogin(login, password));
        response.then()
                .statusCode(SC_OK)
                .body("id", notNullValue());
        courierId = response.jsonPath().getString("id");
        System.out.println(response.body().asString());
    }

    @Test
    public void cannotLoginWithoutLoginAndPassword() {
        CourierLogin courierLogin = new CourierLogin();
        Response response = courierApi.loginCourier(courierLogin);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
        System.out.println(response.body().asString());
    }


    @Test
    public void cannotLoginAbsentUser() {
        CourierLogin courierLogin = new CourierLogin(login, password);
        Response response = courierApi.loginCourier(courierLogin);
        response.then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
        System.out.println(response.body().asString());
    }

}

