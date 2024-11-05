import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(Parameterized.class)
public class ParametrizedOrderTest {
    private static OrderApi orderApi;
    private String[] colors;

    public ParametrizedOrderTest(String[] colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getColors() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList("BLACK")},
                {Arrays.asList("GREY")},
                {Arrays.asList("BLACK", "GREY")},
                {Arrays.asList()}
        });
    }

    @Before
    public void setUp() {
        orderApi = new OrderApi();
    }

    @Test
    public void createOrderWithDifferentColor() {
        Order order = new Order("Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", "5", "2020-06-06", "Saske, come back to Konoha", colors);
        Response response = orderApi.createOrder(order);
        response
                .then()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());

    }




}
