import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import com.github.javafaker.Faker;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CourierTest {
private final Faker faker = new Faker();
    private CourierApi courierApi;
    private String login = faker.name().username();
    private String password = faker.internet().password(3,6);
    private String firstName = faker.name().firstName();



    @Before
    public void setUp() {
        courierApi = new CourierApi();

    }


    @Test
    public void createCourierDone() {
        Courier courier = new Courier(login, password, firstName);
        Response response = courierApi.createCourier(courier);
        response.then()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
        System.out.println(response.body().asString());

    }

    @Test
    public void createCourierRetry() {
        Courier courier = new Courier(login, password, firstName);
        courierApi.createCourier(courier);
        Response response = courierApi.createCourier(courier);
        response.then()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        System.out.println(response.body().asString());
    }


    @Test
    public void cannotCreateCourierWithoutLogin() {
        Courier courier = new Courier(null, password, firstName);
        Response response = courierApi.createCourier(courier);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        System.out.println(response.body().asString());

    }

}
