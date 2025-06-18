package Tests;

import DriverFactory.DriverFactory;
import Listeners.IInvokedListener;
import Listeners.ITestListener;
import Pages.P01_LoginPage;
import Utilities.LogsUtils;
import Utilities.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.SetUpDriver;
import static DriverFactory.DriverFactory.getDriver;
import static Utilities.DataUtils.getPropertyData;


@Listeners({IInvokedListener.class, ITestListener.class})
public class TC01_LoginTest {

    private static final Logger log = LoggerFactory.getLogger(TC01_LoginTest.class);

    @BeforeMethod
    public void SetUp() throws IOException {
        SetUpDriver(Utility.SelectingBrowser());
        LogsUtils.info("Chrome Driver is set up successfully");
        getDriver().get(getPropertyData("environments", "Base_URL"));
        LogsUtils.info("Navigated to the base URL: " + getPropertyData("environments", "Base_URL"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void ValidLoginTC() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton();
        Assert.assertTrue(P01_LoginPage.assertLoginTC(getPropertyData("environments", "HomePageURL")));
    }

    @Test
    public void InValidLoginTCUsingInvalidUsername() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "InvalidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton();
        Assert.assertFalse(P01_LoginPage.assertLoginTC(getPropertyData("environments", "HomePageURL")));
    }

    @Test
    public void InValidLoginTCUsingInvalidPassword() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "InvalidPassword")).
                ClickLoginButton();
        Assert.assertFalse(P01_LoginPage.assertLoginTC(getPropertyData("environments", "HomePageURL")));
    }


    @AfterMethod
    public void TearDown() {
        DriverFactory.quitDriver();
    }

}
