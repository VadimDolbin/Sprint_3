import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest {
    public OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Check that a not empty list of orders is returned")
    @Description(OrderClient.ORDERS_ENDPOINT + " endpoint returns 200 status code and not empty arrays of orders, pageInfo and availableStations")
    public void testNotEmptyOrdersListIsReturned() {
        ValidatableResponse response = orderClient.get();

        response
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("orders", notNullValue())
                .and()
                .body("pageInfo", notNullValue())
                .and()
                .body("availableStations", notNullValue());
    }
}