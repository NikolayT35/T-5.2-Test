package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class RegistrationTest {
    @BeforeEach
    void Setup() {
        open("http://localhost:9999");
    }


    @Test
    void shouldLogin() {
        val user = DataGenerator.registerValidUser();
        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("[data-test-id=action-login] .button__text").click();
        $("h2").shouldHave(text("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldNotLoginBlock() {
        val user = DataGenerator.registerBlockedUser();
        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("[data-test-id=action-login] .button__text").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text(" Ошибка! Пользователь заблокирован")).shouldBe(visible);
    }

    @Test
    void shouldNotLoginWrongLogin() {
        val user = DataGenerator.registerWrongLoginUser();
        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("[data-test-id=action-login] .button__text").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text(" Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldNotLoginWrongPassword() {
        val user = DataGenerator.registerWrongPasswordUser();
        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("[data-test-id=action-login] .button__text").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text(" Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldNotLoginNotExistUser() {
        val user = DataGenerator.registerNotExistUser();
        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("[data-test-id=action-login] .button__text").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text(" Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }
}