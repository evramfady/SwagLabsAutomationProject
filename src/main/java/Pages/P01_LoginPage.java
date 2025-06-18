package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

public class P01_LoginPage {

    private static final By UserName = By.id("user-name");
    private static final By Password = By.id("password");
    private static final By LoginButton = By.id("login-button");

    private static WebDriver driver = null;


    public P01_LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public static boolean assertLoginTC(String expectedUrl) {
        return Objects.equals(driver.getCurrentUrl(), expectedUrl);
    }

    public P01_LoginPage EnterUserName(String userNameText) {
        Utility.SendData(driver, UserName, userNameText);
        return this;
    }

    public P01_LoginPage EnterPassword(String passwordText) {
        Utility.SendData(driver, Password, passwordText);
        return this;
    }

    public P02_ProductsPage ClickLoginButton() {
        Utility.ClickOnElement(driver, LoginButton);
        return new P02_ProductsPage(driver);
    }
}
