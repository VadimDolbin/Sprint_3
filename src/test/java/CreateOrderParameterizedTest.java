import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest {
    public OrderClient orderClient;
    private final List<String> color;
    private Object orderTrack;

    public CreateOrderParameterizedTest(List<String> color, Object orderTrack) {
        this.color = color;
        this.orderTrack = orderTrack;
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @After
    public void tearDown() {
        orderClient.cancel(orderTrack);
    }

    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][]{
                {List.of("BLACK"), notNullValue()},
                {List.of("GREY"), notNullValue()},
                {List.of("BLACK", "GREY"), notNullValue()},
                {null, notNullValue()},
        };
    }

    @Test
    @DisplayName("Check that a new order can be created using all possible values of color field")
    @Description("/api/v1/orders endpoint returns 201 status code and body 'track': 'not null'")
    public void testOrderIsCreatedUsingColorValues() {
        Order order = new Order(color);

        ValidatableResponse response = orderClient.create(order);
        response
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("track", (Matcher<?>) orderTrack);

        orderTrack = response.extract().path("track");
    }
}