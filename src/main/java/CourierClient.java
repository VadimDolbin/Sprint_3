import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {
    public static final String COURIER_ENDPOINT = "/api/v1/courier";
    public static final String COURIER_LOGIN_ENDPOINT = "/api/v1/courier/login";

    @Step("Send POST request to " + COURIER_ENDPOINT + " to create a courier")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(courier)
                .when()
                .post("/courier")
                .then();
    }

    @Step("Send POST request to " + COURIER_LOGIN_ENDPOINT + " to log in as a selected courier")
    public ValidatableResponse login(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(courierCredentials)
                .when()
                .post("/courier/login")
                .then();
    }

    @Step("Send DELETE request to " + COURIER_ENDPOINT + " to delete a selected courier")
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete("/courier/" + courierId)
                .then();
    }
}