import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {
    public static final String ORDERS_ENDPOINT = "/api/v1/orders";
    public static final String ORDERS_CANCEL_ENDPOINT = "/api/v1/orders/cancel";

    @Step("Send POST request to " + ORDERS_ENDPOINT + " to create an order")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(order)
                .when()
                .post("/orders")
                .then();
    }

    @Step("Send PUT request to " + ORDERS_CANCEL_ENDPOINT + " to cancel an order")
    public ValidatableResponse cancel(Object orderTrack) {
        return given()
                .spec(getBaseSpec())
                .when()
                .put("/orders/cancel/" + orderTrack)
                .then();
    }

    @Step("Send GET request to " + ORDERS_ENDPOINT + " to get a list of all orders")
    public ValidatableResponse get() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get("/orders")
                .then();
    }
}