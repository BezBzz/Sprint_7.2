import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderTest {

    private OrderApi orderApi;

    @Before
    public void setUp() {
    orderApi = new OrderApi();
    }


    @Test
    public void getAllOrders() {
        Order order = new Order();
        Response response = orderApi.getOrder();
        response
                .then()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
        System.out.println(response.body().asString());
    }

}
