package Tests;

import Listeners.IInvokedListener;
import Listeners.ITestListener;
import Pages.P01_LoginPage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static DriverFactory.DriverFactory.getDriver;
import static Utilities.DataUtils.getPropertyData;
import static org.assertj.core.api.Assertions.assertThat;

@Listeners({IInvokedListener.class, ITestListener.class})
public class TC01_LoginTest extends BaseTest {

    @Test
    public void ValidLoginTC() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton();
        assertThat(P01_LoginPage.assertLoginTC(getPropertyData("environments", "HomePageURL")))
                .withFailMessage("Login failed with valid credentials")
                .isTrue();
    }

    @Test
    public void InValidLoginTCUsingInvalidUsername() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "InvalidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton();
        assertThat(P01_LoginPage.assertLoginTC(getPropertyData("environments", "HomePageURL")))
                .withFailMessage("Login succeeded with invalid username")
                .isFalse();
    }

    @Test
    public void InValidLoginTCUsingInvalidPassword() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "InvalidPassword")).
                ClickLoginButton();
        assertThat(P01_LoginPage.assertLoginTC(getPropertyData("environments", "HomePageURL")))
                .withFailMessage("Login succeeded with invalid password")
                .isFalse();
    }
}