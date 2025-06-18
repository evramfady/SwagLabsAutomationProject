package Tests;

import DriverFactory.DriverFactory;
import Listeners.IInvokedListener;
import Listeners.ITestListener;
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
public class TC05_OverViewTest {

    private static final Logger log = LoggerFactory.getLogger(TC05_OverViewTest.class);

    @BeforeMethod
    public void SetUp() throws IOException {
        SetUpDriver(Utility.SelectingBrowser());
        LogsUtils.info("Edge Driver is set up successfully");
        getDriver().get(getPropertyData("environments", "Base_URL"));
        LogsUtils.info("Navigated to the base URL: " + getPropertyData("environments", "Base_URL"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void VerifyTotalAndClickFinishButton() throws IOException {
        //TODO: Implement the logic to select products based on the properties file
        new Pages.P01_LoginPage(getDriver())
                .EnterUserName(getPropertyData("LogInData", "ValidUserName"))
                .EnterPassword(getPropertyData("LogInData", "ValidPassword"))
                .ClickLoginButton();
        //TODO: Select products based on the properties file
        new Pages.P02_ProductsPage(getDriver())
                .ClickAddToCartButtonForRandomProd(
                        Integer.parseInt(getJsonData("ProductsData", "SelectedProducts")),
                        Integer.parseInt(getJsonData("ProductsData", "NumOfAllProducts")))
                .ClickCartIcon();
        //TODO: Implement the logic to fill the checkout form based on the properties file
        new Pages.P03_CartPage(getDriver())
                .ClickOnCheckoutPage()
                .FillingCheckOutForm()
                .ClickContinueButton();
        //TODO: Verify the shipment information page
        new Pages.P05_OverViewPage(getDriver())
                .VerifyTotal();

        Assert.assertTrue(new P05_OverViewPage(getDriver()).VerifyTotal());


    }

    @AfterMethod
    public void TearDown() {
        DriverFactory.quitDriver();
    }
}



