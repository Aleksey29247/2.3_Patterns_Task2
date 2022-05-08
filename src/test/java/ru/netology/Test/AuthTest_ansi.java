package ru.netology.Test;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import ru.netology.Data.DataGenerator;


import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;


import ru.netology.Data.RegistrationDto;


public class AuthTest_ansi {

    @Test
    void shouldLoginActiveUser() {
        //�������� ������������
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationDto  user = DataGenerator.getRegisteredUser("active");
        $x("//input[@type='text']").val(user.getLogin());
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(), '������ �������')]").should(Condition.visible);
    }

    @Test
    void shouldLoginBlockedUser() {
        //��������������� ������������
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationDto  user = DataGenerator.getRegisteredUser("blocked");
        $x("//input[@type='text']").val(user.getLogin());
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']").should(Condition.visible, Duration.ofSeconds(10));


    }

    @Test
        //������ ��������� �����
    void shouldWrongLogin() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationDto  user = DataGenerator.getRegisteredUser("blocked");
        $x("//input[@type='text']").val( DataGenerator.getRandomLogin());
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']")
                .shouldHave(Condition.text("������! ������� ������ ����� ��� ������"), Duration.ofSeconds(10));


    }

    @Test
        //������ ��������� ������
    void shouldWrongPassword() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationDto  user = DataGenerator.getRegisteredUser("blocked");
        $x("//input[@type='text']").val(user.getLogin());
        $x("//input[@type='password']").val( DataGenerator.getRandomPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']")
                .shouldHave(Condition.text("������! ������� ������ ����� ��� ������"), Duration.ofSeconds(10));


    }

    @Test
        // ������ �����������
    void shouldNoPassword() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationDto user = DataGenerator.getRegisteredUser("blocked");
        $x("//input[@type='text']").val(user.getLogin());
        $x("//span[@class='button__text']").click();
        $("[data-test-id='password'].input_invalid .input__sub")
                .shouldHave(Condition.text("���� ����������� ��� ����������"), Duration.ofSeconds(10));


    }

    @Test
        //���� ������ �����������
    void shouldNoLogin() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationDto user = DataGenerator.getRegisteredUser("blocked");
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $("[data-test-id='login'].input_invalid .input__sub")
                .shouldHave(Condition.text("���� ����������� ��� ����������"), Duration.ofSeconds(10));
    }
}