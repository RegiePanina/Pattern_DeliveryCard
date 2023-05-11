package ru.netology.service;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import service.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $(".calendar-input input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $(".calendar-input input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(30));
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate),
                        Duration.ofSeconds(15));

        //

        $(".calendar-input input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $(".calendar-input input").setValue(secondMeetingDate);
        $$("button").find(exactText("Запланировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(30));
        $("[data-test-id=replan-notification]")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"),
                        Duration.ofSeconds(15));
        $(withText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate),
                        Duration.ofSeconds(15));

    }
}

