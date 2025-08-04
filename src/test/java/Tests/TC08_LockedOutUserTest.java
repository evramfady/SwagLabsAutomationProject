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
public class TC08_LockedOutUserTest extends BaseTest {

    @Test
    public void LockedOutUserTC() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "LockedOutUser")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton();
        assertThat(P01_LoginPage.assertLoginTC(getPropertyData("environments", "HomePageURL")))
                .withFailMessage("Login succeeded with locked out user")
                .isFalse();
    }
}
