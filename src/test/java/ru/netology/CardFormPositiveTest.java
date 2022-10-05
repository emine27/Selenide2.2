package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardFormPositiveTest {
    String date(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldBeSuccessfullyBooked() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).should(exactText("Встреча успешно забронирована на " + planningDate));
    }


    @Test
    void shouldSuccessWithoutSurname() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).should(exactText("Встреча успешно забронирована на " + planningDate));
    }

    @Test
    void shouldSuccessOnlyShortName() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Э");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).should(exactText("Встреча успешно забронирована на " + planningDate));

    }

    @Test
    void shouldSuccessNameAndSurnameSwap(){
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).should(exactText("Встреча успешно забронирована на " + planningDate));
    }
}
