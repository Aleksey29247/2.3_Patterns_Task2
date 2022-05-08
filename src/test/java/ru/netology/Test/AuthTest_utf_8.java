package ru.netology.Test;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import ru.netology.Data.DataGenerator;
import ru.netology.Data.*;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;


import ru.netology.Data.RegistrationDto;

import static io.restassured.RestAssured.given;

public class AuthTest_utf_8 {

    @Test
    void shouldLoginActiveUser() {
        //Активный пользователь
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationDto  user = DataGenerator.getRegisteredUser("active");
        $x("//input[@type='text']").val(user.getLogin());
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(), 'Личный кабинет')]").should(Condition.visible);
    }

    @Test
    void shouldLoginBlockedUser() {
        //Заблокированный пользователь
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationDto  user = DataGenerator.getRegisteredUser("blocked");
        $x("//input[@type='text']").val(user.getLogin());
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']").should(Condition.visible, Duration.ofSeconds(10));


    }

    @Test
        //Введен рандомный логин
    void shouldWrongLogin() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationDto  user = DataGenerator.getRegisteredUser("blocked");
        $x("//input[@type='text']").val( DataGenerator.getRandomLogin());
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10));


    }

    @Test
        //Введен рандомный пароль
    void shouldWrongPassword() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationDto  user = DataGenerator.getRegisteredUser("blocked");
        $x("//input[@type='text']").val(user.getLogin());
        $x("//input[@type='password']").val( DataGenerator.getRandomPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10));


    }

    @Test
        // пароль отсутствует
    void shouldNoPassword() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationDto user = DataGenerator.getRegisteredUser("blocked");
        $x("//input[@type='text']").val(user.getLogin());
        $x("//span[@class='button__text']").click();
        $("[data-test-id='password'].input_invalid .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"), Duration.ofSeconds(10));


    }

    @Test
        //Ввод логина отсутствует
    void shouldNoLogin() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationDto user = DataGenerator.getRegisteredUser("blocked");
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $("[data-test-id='login'].input_invalid .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"), Duration.ofSeconds(10));
    }


}
