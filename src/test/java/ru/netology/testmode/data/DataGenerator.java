package ru.netology.testmode.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final Faker faker = new Faker();
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .build();

    private DataGenerator() {
    }

    public static String getRandomLogin() {
        return faker.name().username();
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }

    private static void sendRequest(RegistrationDto user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static class Registration {
        public static RegistrationDto getRegisteredUser(String status) {
            RegistrationDto user = getUser(status);
            sendRequest(user);
            return user;
        }

        public static RegistrationDto getUser(String status) {
            String login = getRandomLogin();
            String password = getRandomPassword();
            return new RegistrationDto(login, password, status);
        }
    }
}