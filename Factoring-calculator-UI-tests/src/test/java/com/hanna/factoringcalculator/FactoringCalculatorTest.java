package com.hanna.factoringcalculator;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.Duration;

public class FactoringCalculatorTest {

    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        Configuration.baseUrl = "https://www.swedbank.lt";
        open("/business/finance/trade/factoring?language=ENG");
    }

    @Test
    public void enterValidData() {
        open("/business/finance/trade/factoring?language=ENG");

        $("[name='calc_d5']").shouldBe(visible, enabled).setValue("10000");
        $("[name='calc_d6']").shouldBe(visible, enabled).selectOptionByValue("90");
        $("[name='calc_d7']").shouldBe(visible, enabled).setValue("3");
        $("[name='calc_d8']").shouldBe(visible, enabled).selectOptionByValue("90");
        $("[name='calc_d9']").shouldBe(visible, enabled).setValue("0.3");

        $("#calculate-factoring").scrollIntoView(true).click();


    }

    @Test
    public void enterEmtyData() {
        open("/business/finance/trade/factoring?language=ENG");
        $("[name='calc_d5']").shouldBe(visible, enabled).setValue("");
        $("[name='calc_d7']").shouldBe(visible, enabled).setValue("");
        $("[name='calc_d9']").shouldBe(visible, enabled).setValue("0.3");
        $("#calculate-factoring").scrollIntoView(true).click();
        $("ui-hint[type='error'][error-type='valueMissing']").shouldHave(text("Please fill out this field."));

    }

    @Test
    public void enterInvoiceAmountLessThanOne() {
        open("/business/finance/trade/factoring?language=ENG");

        $("[name='calc_d5']").shouldBe(visible, enabled).setValue("0.4");
        $("ui-hint[error-type='rangeUnderflow']")
                .shouldHave(text("Value must be greater than or equal 1."));



    }

    @Test
    public void correctResult() {
        open("/business/finance/trade/factoring?language=ENG");

        $("[name='calc_d5']").shouldBe(visible, enabled).setValue("10000");
        $("[name='calc_d6']").shouldBe(visible, enabled).selectOptionByValue("90");
        $("[name='calc_d7']").shouldBe(visible, enabled).setValue("5");
        $("[name='calc_d8']").shouldBe(visible, enabled).selectOptionByValue("30");
        $("[name='calc_d9']").shouldBe(visible, enabled).setValue("0.5");

        $("#calculate-factoring").scrollIntoView(true).click();

        $("#result_perc").shouldHave(text("0.88"));
        $("#result").shouldHave(text("87.50"));
    }

    @Test
    public void enterInterestRateLessThanZero() {
        open("/business/finance/trade/factoring?language=ENG");

        $("[name='calc_d7']").shouldBe(visible, enabled).setValue("-8");

        $("ui-hint[error-type='rangeUnderflow']")
                .shouldHave(text("Value must be greater than or equal 0."));
    }

    @Test
    public void shouldWorkOnMobileDimensions() {
        Configuration.browserSize = "375x677";

        open("/business/finance/trade/factoring?language=ENG");

        $("[name='calc_d5']").shouldBe(visible, enabled).setValue("100");
        $("[name='calc_d6']").shouldBe(visible, enabled).selectOptionByValue("80");
        $("[name='calc_d7']").shouldBe(visible, enabled).setValue("5");
        $("[name='calc_d8']").shouldBe(visible, enabled).selectOptionByValue("120");
        $("[name='calc_d9']").shouldBe(visible, enabled).setValue("0.5");

        $("#calculate-factoring").scrollIntoView(true).shouldBe(visible, enabled).click();

        $("#result_perc").shouldNotBe(empty);
        $("#result").shouldNotBe(empty);

    }

    @Test
    public void testResultOverflow() {
        Configuration.browserSize = "1920x1080";
        open("/business/finance/trade/factoring?language=ENG");

        $("[name='calc_d5']").scrollIntoView(true).shouldBe(visible, enabled).setValue("1000000000000");
        $("[name='calc_d6']").scrollIntoView(true).shouldBe(visible, enabled).selectOptionByValue("80");
        $("[name='calc_d7']").scrollIntoView(true).shouldBe(visible, enabled).setValue("0.4");
        $("[name='calc_d8']").scrollIntoView(true).shouldBe(visible, enabled).selectOptionByValue("30");
        $("[name='calc_d9']").scrollIntoView(true).shouldBe(visible, enabled).setValue("3");

        $("#calculate-factoring").scrollIntoView(true).shouldBe(visible, enabled).click();


        SelenideElement screen = $("body").shouldBe(visible, Duration.ofSeconds(10));
        boolean isScreenScrollable = executeJavaScript("return arguments[0].scrollWidth > arguments[0].clientWidth;", screen);
        assertTrue(isScreenScrollable, "The entire screen should be scrollable horizontally.");
    }
}
