package Tests;

import DriverFactory.DriverFactory;
import Listeners.IInvokedListener;
import Listeners.ITestListener;
import Pages.P01_LoginPage;
import Pages.P02_ProductsPage;
import Pages.P03_CartPage;
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
public class TC03_CartTest {

    private static final Logger log = LoggerFactory.getLogger(Tests.TC03_CartTest.class);

    @BeforeMethod
    public void SetUp() throws IOException {
        SetUpDriver(Utility.SelectingBrowser());
        LogsUtils.info("Chrome Driver is set up successfully");
        getDriver().get(getPropertyData("environments", "Base_URL"));
        LogsUtils.info("Navigated to the base URL: " + getPropertyData("environments", "Base_URL"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void ComparingPricesTC() throws IOException {
        float TotalPrice = new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton().
                ClickAddToCartButtonForRandomProd(
                        Integer.parseInt(getJsonData("ProductsData", "SelectedProducts")),
                        Integer.parseInt(getJsonData("ProductsData", "SelectedProducts"))).
                GetTotalPriceForItems();
        new P02_ProductsPage(getDriver()).ClickCartIcon();
        Assert.assertTrue(new P03_CartPage(getDriver()).ComparingTotalPrices(TotalPrice),
                "The total price of items in the cart does not match the expected total price.");
    }


    @AfterMethod
    public void TearDown() {
        DriverFactory.quitDriver();
    }
}
