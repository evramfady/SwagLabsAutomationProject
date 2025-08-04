package Tests;

import DriverFactory.DriverFactory;
import Utilities.LogsUtils;
import Utilities.Utility;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;

import static DriverFactory.DriverFactory.getDriver;
import static Utilities.DataUtils.getPropertyData;

public class BaseTest {

    @BeforeMethod
    public void setup() throws IOException {
        DriverFactory.SetUpDriver(Utility.SelectingBrowser());
        LogsUtils.info("Driver is set up successfully");
        getDriver().get(getPropertyData("environments", "Base_URL"));
        LogsUtils.info("Navigated to the base URL: " + getPropertyData("environments", "Base_URL"));
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
