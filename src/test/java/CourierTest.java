import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.equalTo;

public class CourierTest {

    private CourierApi courierApi;
    private String login = "ninja";
    private String password = "1234";
    private String firstName = "saske";
    private int courierId;


    @Before
    public void setUp() {
        courierApi = new CourierApi();

    }
    @After
    public void tearDown() {
        Response loginResponse = courierApi.loginCourier(login, password);
        int courierId = loginResponse.path("id");
        courierApi.deleteCourier(courierId);
    }

    @Test
    public void createCourierDone() {
        Courier courier = new Courier(login, password, firstName);
        Response response = courierApi.createCourier(courier);
        response.then()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
        courierId = response.jsonPath().getInt("id");
    }

    @Test
    public void createCourierRetry() {
        Courier courier = new Courier(login, password, firstName);
        courierApi.createCourier(courier);
        Response response = courierApi.createCourier(courier);
        response.then()
                .statusCode(SC_CREATED)
                .body("message", equalTo("Этот логин уже используется"));

    }


    @Test
    public void cannotCreateCourierWithoutLogin() {
        Courier courier = new Courier(null, password, firstName);
        Response response = courierApi.createCourier(courier);
        response.then()
                .statusCode(SC_CREATED)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));


    }

}
