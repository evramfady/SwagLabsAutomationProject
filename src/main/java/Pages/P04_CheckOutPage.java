package Pages;

import Utilities.DataUtils;
import Utilities.Utility;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class P04_CheckOutPage {

    private static final By FirstName = By.id("first-name");
    private static final By LastName = By.id("last-name");
    private static final By PostalCode = By.id("postal-code");
    private static final By ContinueButton = By.xpath("//input[@value='CONTINUE']");

    private final WebDriver driver;

    public P04_CheckOutPage(WebDriver driver) {
        this.driver = driver;
    }

    public P04_CheckOutPage FillingCheckOutForm() throws IOException {
        Utility.SendData(driver, FirstName,
                DataUtils.getJsonData("PersonalShipmentData", "FirstName") + "-" + Utility.getTimeDateStamp());
        Utility.SendData(driver, LastName,
                DataUtils.getJsonData("PersonalShipmentData", "LastName") + "-" + Utility.getTimeDateStamp());
        Utility.SendData(driver, PostalCode, new Faker().number().digits(5));
        return this;
    }


    public P05_OverViewPage ClickContinueButton() {
        Utility.ClickOnElement(driver, ContinueButton);
        return new P05_OverViewPage(driver);
    }
}
