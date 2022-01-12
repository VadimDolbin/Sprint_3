import com.github.javafaker.Faker;

import java.util.List;

public class Order {
    public final String firstName;
    public final String lastName;
    public final String address;
    public final String metroStation;
    public final String phone;
    public final int rentTime;
    public final String deliveryDate;
    public final String comment;
    public final List<String> color;

    public Order(List<String> color) {
        Faker faker = new Faker();

        this.firstName = faker.name().firstName();
        this.lastName = faker.name().lastName();;
        this.address = faker.address().fullAddress();
        this.metroStation = faker.number().digits(2);
        this.phone = faker.phoneNumber().cellPhone();
        this.rentTime = faker.random().nextInt(24);
        this.deliveryDate = faker.backToTheFuture().date();
        this.comment = faker.nation().language();
        this.color = color;
    }
}