import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(Parameterized.class)

public class ParametrizedOrderTest {

    private final Faker faker = new Faker();
    private OrderApi orderApi;
    private String[] colors;
    private String firstName = faker.name().firstName();
    private String lastName = faker.name().lastName();
    private String address = faker.address().fullAddress();
    private String metroStation = faker.address().state();
    ;
    private String phone = faker.phoneNumber().cellPhone();
    private String rentTime = faker.date().toString();
    private String deliveryDate = faker.date().toString();
    private String comment = faker.name().title();


    public ParametrizedOrderTest(String[] colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[] getColors() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        };
    }

    @Before
    public void setUp() {
        orderApi = new OrderApi();
    }

    @Test
    public void createOrderWithDifferentColor() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colors);
        Response response = orderApi.createOrder(order);
        response
                .then()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
        System.out.println(response.body().asString());

    }


}
