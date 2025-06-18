package Tests;

import DriverFactory.DriverFactory;
import Listeners.IInvokedListener;
import Listeners.ITestListener;
import Pages.P01_LoginPage;
import Pages.P05_OverViewPage;
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
import static Utilities.DataUtils.getJsonData;
import static Utilities.DataUtils.getPropertyData;

@Listeners({IInvokedListener.class, ITestListener.class})
public class TC04_CheckOutPage {

    private static final Logger log = LoggerFactory.getLogger(TC04_CheckOutPage.class);

    @BeforeMethod
    public void SetUp() throws IOException {
        SetUpDriver(Utility.SelectingBrowser());
        LogsUtils.info("Edge Driver is set up successfully");
        getDriver().get(getPropertyData("environments", "Base_URL"));
        LogsUtils.info("Navigated to the base URL: " + getPropertyData("environments", "Base_URL"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void VerifyFillingFormAndGotoOverviewpage() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton().
                ClickAddToCartButtonForRandomProd(
                        Integer.parseInt(getJsonData("ProductsData", "SelectedProducts")),
                        Integer.parseInt(getJsonData("ProductsData", "SelectedProducts"))).
                ClickCartIcon().
                ClickOnCheckoutPage().
                FillingCheckOutForm().
                ClickContinueButton();
        Assert.assertTrue(new P05_OverViewPage(getDriver()).VerifyShipmentInfoPage(),
                "The shipment information page is not displayed as expected.");
    }

    @AfterMethod
    public void TearDown() {
        DriverFactory.quitDriver();
    }
}



