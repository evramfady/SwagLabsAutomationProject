package Pages;

import Utilities.Utility;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P01_LoginPage {

    private final By UserName = By.id("user-name");
    private final By Password = By.id("password");
    private final By LoginButton = By.id("login-button");

    // GOOD: The driver is now a final instance variable, not static.
    private final WebDriver driver;


    public P01_LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Entering username: {userNameText}")
    public P01_LoginPage EnterUserName(String userNameText) {
        Utility.SendData(driver, UserName, userNameText);
        return this;
    }

    @Step("Entering password")
    public P01_LoginPage EnterPassword(String passwordText) {
        Utility.SendData(driver, Password, passwordText);
        return this;
    }

    @Step("Clicking the login button")
    public P02_ProductsPage ClickLoginButton() {
        Utility.ClickOnElement(driver, LoginButton);
        return new P02_ProductsPage(driver);
    }
}