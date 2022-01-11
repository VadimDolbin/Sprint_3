import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    @Step("Send POST request to /api/v1/orders to create an order")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    @Step("Send PUT request to /api/v1/orders/cancel to cancel an order")
    public ValidatableResponse cancel(Object orderTrack) {
        return given()
                .spec(getBaseSpec())
                .when()
                .put("/api/v1/orders/cancel/" + orderTrack)
                .then();
    }

    @Step("Send GET request to /api/v1/orders to get a list of all orders")
    public ValidatableResponse get() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get("/api/v1/orders")
                .then();
    }
}