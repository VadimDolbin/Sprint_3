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
    @Description("api/v1/courier endpoint returns 200 status code and not empty body")
    public void testNotEmptyOrdersListIsReturned() {
        ValidatableResponse response = orderClient.get();

        response
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body(notNullValue());
    }
}