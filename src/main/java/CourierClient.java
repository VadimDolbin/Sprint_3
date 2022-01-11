import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {

    @Step("Send POST request to /api/v1/courier to create a courier")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    @Step("Send POST request to /api/v1/courier/login to log in as a selected courier")
    public ValidatableResponse login(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(courierCredentials)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    @Step("Send DELETE request to /api/v1/courier/ to delete a selected courier")
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete("/api/v1/courier/" + courierId)
                .then();
    }
}