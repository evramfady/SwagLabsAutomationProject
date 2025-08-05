package Tests;

import Listeners.IInvokedListener;
import Listeners.ITestListener;
import Pages.P01_LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static DriverFactory.DriverFactory.getDriver;
import static Utilities.DataUtils.getPropertyData;
import static org.assertj.core.api.Assertions.assertThat;

@Listeners({IInvokedListener.class, ITestListener.class})
public class TC01_LoginTest extends BaseTest {

    @Test(description = "Verify successful login with valid credentials")
    @Description("This test attempts to log in using a standard user and verifies redirection to the inventory page.")
    @Severity(SeverityLevel.BLOCKER)
    public void ValidLoginTC() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton();

        // GOOD: Assertion logic is now inside the test, not in a static page method.
        String expectedUrl = getPropertyData("environments", "HomePageURL");
        assertThat(getDriver().getCurrentUrl())
                .withFailMessage("Login failed with valid credentials")
                .isEqualTo(expectedUrl);
    }

    @Test(description = "Verify login fails with an invalid username")
    @Description("This test validates that a user cannot log in with an incorrect username.")
    @Severity(SeverityLevel.CRITICAL)
    public void InValidLoginTCUsingInvalidUsername() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "InvalidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton();

        String homePageUrl = getPropertyData("environments", "HomePageURL");
        assertThat(getDriver().getCurrentUrl())
                .withFailMessage("Login succeeded with invalid username")
                .isNotEqualTo(homePageUrl);
    }

    @Test(description = "Verify login fails with an invalid password")
    @Description("This test validates that a user cannot log in with an incorrect password.")
    @Severity(SeverityLevel.CRITICAL)
    public void InValidLoginTCUsingInvalidPassword() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "InvalidPassword")).
                ClickLoginButton();

        String homePageUrl = getPropertyData("environments", "HomePageURL");
        assertThat(getDriver().getCurrentUrl())
                .withFailMessage("Login succeeded with invalid password")
                .isNotEqualTo(homePageUrl);
    }

    @Test(description = "Verify that a locked-out user cannot log in")
    @Description("This test ensures that a user with locked-out credentials cannot access the inventory page.")
    @Severity(SeverityLevel.CRITICAL)
    public void lockedOutUserTC() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "LockedOutUser")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton();

        String homePageUrl = getPropertyData("environments", "HomePageURL");
        assertThat(getDriver().getCurrentUrl())
                .withFailMessage("Login succeeded with a locked-out user")
                .isNotEqualTo(homePageUrl);
    }

    @Test(description = "Verify that a problem user can log in")
    @Description("This test ensures that a user with problem credentials can access the inventory page.")
    @Severity(SeverityLevel.NORMAL)
    public void problemUserTC() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ProblemUser")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton();

        String homePageUrl = getPropertyData("environments", "HomePageURL");
        assertThat(getDriver().getCurrentUrl())
                .withFailMessage("Login failed with a problem user")
                .isEqualTo(homePageUrl);
    }

    @Test(description = "Verify that a performance glitch user can log in")
    @Description("This test ensures that a user with performance glitch credentials can access the inventory page.")
    @Severity(SeverityLevel.NORMAL)
    public void performanceGlitchUserTC() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "PerformanceGlitchUser")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton();

        String homePageUrl = getPropertyData("environments", "HomePageURL");
        assertThat(getDriver().getCurrentUrl())
                .withFailMessage("Login failed with a performance glitch user")
                .isEqualTo(homePageUrl);
    }
}