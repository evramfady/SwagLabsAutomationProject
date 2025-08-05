package Pages;

import Utilities.DataUtils;
import Utilities.Utility;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class P04_CheckOutPage {

    private final By FirstName = By.id("first-name");
    private final By LastName = By.id("last-name");
    private final By PostalCode = By.id("postal-code");
    private final By ContinueButton = By.cssSelector("input[value='CONTINUE']");

    private final WebDriver driver;

    public P04_CheckOutPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Filling the checkout form with user data")
    public P04_CheckOutPage FillingCheckOutForm() throws IOException {
        Utility.SendData(driver, FirstName,
                DataUtils.getJsonData("PersonalShipmentData", "FirstName") + "-" + Utility.getTimeDateStamp());
        Utility.SendData(driver, LastName,
                DataUtils.getJsonData("PersonalShipmentData", "LastName") + "-" + Utility.getTimeDateStamp());
        Utility.SendData(driver, PostalCode, new Faker().number().digits(5));
        return this;
    }

    @Step("Clicking the continue button")
    public P05_OverViewPage ClickContinueButton() {
        Utility.ClickOnElement(driver, ContinueButton);
        return new P05_OverViewPage(driver);
    }
}
