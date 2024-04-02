package com.hanna.factoringcalculator;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.Configuration;
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

}
