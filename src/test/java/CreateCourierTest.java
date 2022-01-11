import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
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
    @DisplayName("Check that a new courier can be created using all required fields")
    @Description("api/v1/courier endpoint returns 201 status code and body 'ok': 'true'")
    public void testCourierIsCreatedWithRequiredFields() {
        Courier courier = Courier.getRandom();
        ValidatableResponse response = courierClient.create(courier);

        response
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier)).extract().path("id");;
    }

    @Test
    @DisplayName("Check that a duplicated courier can not be created")
    @Description("api/v1/courier endpoint returns 409 status code and body 'message': 'Этот логин уже используется'")
    public void testDuplicatedCourierIsNotCreated() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        ValidatableResponse response = courierClient.create(courier);

        response
                .assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Check that a courier with duplicated login can not be created")
    @Description("api/v1/courier endpoint returns 409 status code and body 'message': 'Этот логин уже используется'")
    public void testCourierIsNotCreatedWithDuplicatedLogin() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        ValidatableResponse response = courierClient.create(new Courier(courier.login, "TestPassword", "TestFirstName"));

        response
                .assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Check that a new courier can not be created without a login")
    @Description("api/v1/courier endpoint returns 400 status code and body 'message': 'Недостаточно данных для создания учетной записи'")
    public void testCourierIsNotCreatedWithoutLogin() {
        Courier courier = Courier.getRandomWithoutLogin();
        ValidatableResponse response = courierClient.create(courier);

        response
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check that a new courier can not be created without a password")
    @Description("api/v1/courier endpoint returns 400 status code and body 'message': 'Недостаточно данных для создания учетной записи'")
    public void testCourierIsNotCreatedWithoutPassword() {
        Courier courier = Courier.getRandomWithoutPassword();
        ValidatableResponse response = courierClient.create(courier);

        response
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check that a new courier can be created without a first name")
    @Description("api/v1/courier endpoint returns 201 status code and body 'ok': 'true'")
    public void testCourierIsCreatedWithoutFirstName() {
        Courier courier = Courier.getRandomWithoutFirstName();
        ValidatableResponse response = courierClient.create(courier);

        response
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier)).extract().path("id");;
    }
}