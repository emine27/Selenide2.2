package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardFormNegativeTest {
    String date(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void  shouldNoSuccessfulWithEmptyCity() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=city].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Поле обязательно для заполнения"));
    }


    @Test
    void shouldNoSuccessfulWithWrongCity() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Будапешт");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=city].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNoSuccessfulWithNameInEng() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Ivanov Ivan");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=name].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNoSuccessfulNameWithSymbol() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Николай 2-й");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=name].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNoSuccessfulWithoutNameAndSurname() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=name].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNoSuccessful2DaysAfterDate() {
        String planningDate = date(2);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Иванов Василий");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=date] .input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldNoSuccessfulToday() {
        String planningDate = date(0);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=date] .input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldNoSuccessfulWithWrongPhoneSymbol() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("!78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNoSuccessfulWhenPhoneOver11Symbol() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+799999999999");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNoSuccessfulWhenPhoneLess11Symbol() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Иванов Василий");
        $("[data-test-id=phone] input").setValue("+7999999999");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNoSuccessfulWhenPhoneWithoutPlus() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Васильевич Василий");
        $("[data-test-id=phone] input").setValue("79999999999");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNoSuccessfulWithoutPhone() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNoSuccessfulWithoutCheckBox() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=agreement].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }






}
