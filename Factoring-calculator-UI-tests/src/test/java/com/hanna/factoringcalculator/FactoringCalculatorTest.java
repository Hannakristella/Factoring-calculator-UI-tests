package com.hanna.factoringcalculator;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

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
    public void enterInvalidData() {

    }


}