package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;


import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private DataGenerator() {
    }

    private static Faker faker = new Faker((new Locale("ru")));

    @BeforeAll
    static void setUp(Registration registered) {
        given()
                .spec(requestSpec)
                .body(registered)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static Registration registerValidUser() {
        val validUser = new Registration(
                faker.name().username(),
                faker.internet().password(),
                "active"
        );
        setUp(validUser);
        return validUser;
    }

    public static Registration registerBlockedUser() {
        val validUser = new Registration(
                faker.name().username(),
                faker.internet().password(),
                "blocked"
        );
        setUp(validUser);
        return validUser;
    }

    public static Registration registerWrongLoginUser() {
        val validUser = new Registration(
                faker.name().username(),
                "password",
                "active"
        );
        val invalidUser = new Registration(
                faker.name().username(),
                "password",
                "active"
        );
        setUp(validUser);
        return invalidUser;
    }

    public static Registration registerWrongPasswordUser() {
        val validUser = new Registration(
                "Иванов Иван",
                faker.internet().password(),
                "active"
        );
        val invalidUser = new Registration(
                "Иванов Иван",
                faker.internet().password(),
                "active"
        );
        setUp(validUser);
        return invalidUser;
    }

    public static Registration registerNotExistUser() {
        val validUser = new Registration(
                faker.name().username(),
                faker.internet().password(),
                "active"
        );
        return validUser;
    }
}

