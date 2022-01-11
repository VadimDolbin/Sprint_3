import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    public CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Check that a new courier can be logged in using all required fields")
    @Description("/api/v1/courier/login endpoint returns 200 status code and body 'id': 'not null'")
    public void testCourierIsLoggedInUsingRequiredFields() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        ValidatableResponse response = courierClient.login(CourierCredentials.getCourierCredentials(courier));

        response
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue());

        courierId = response.extract().path("id");
    }

    @Test
    @DisplayName("Check that a new courier can not be logged in without a login")
    @Description("/api/v1/courier/login endpoint returns 400 status code and body 'message': 'Недостаточно данных для входа'")
    public void testCourierIsNotLoggedInWithoutLogin() {
        Courier courier = Courier.getRandomWithoutLogin();
        courierClient.create(courier);
        ValidatableResponse response = courierClient.login(CourierCredentials.getCourierCredentials(courier));

        response
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Check that a new courier can not be logged in without a password")
    @Description("/api/v1/courier/login endpoint returns 400 status code and body 'message': 'Недостаточно данных для входа'")
    public void testCourierIsNotLoggedInWithoutPassword() {
        Courier courier = Courier.getRandomWithoutPassword();
        courierClient.create(courier);
        ValidatableResponse response = courierClient.login(CourierCredentials.getCourierCredentials(courier));

        response
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Check that non-existing courier can not be logged in using all required fields")
    @Description("/api/v1/courier/login endpoint returns 404 status code and body 'message': 'Учетная запись не найдена'")
    public void testNonExistingCourierIsNotLoggedInUsingRequiredFields() {
        ValidatableResponse response = courierClient.login(new CourierCredentials("NonExistingUser", "NonExistingPassword"));

        response
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }
}