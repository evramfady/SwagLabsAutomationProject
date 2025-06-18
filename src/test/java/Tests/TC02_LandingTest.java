package Tests;

import Listeners.IInvokedListener;
import Listeners.ITestListener;
import Pages.P01_LoginPage;
import Pages.P02_ProductsPage;
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

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getJsonData;
import static Utilities.DataUtils.getPropertyData;


@Listeners({IInvokedListener.class, ITestListener.class})
public class TC02_LandingTest {

    private static final Logger log = LoggerFactory.getLogger(TC02_LandingTest.class);

    @BeforeMethod
    public void SetUp() throws IOException {
        SetUpDriver(Utility.SelectingBrowser());
        LogsUtils.info("Chrome Driver is set up successfully");
        getDriver().get(getPropertyData("environments", "Base_URL"));
        LogsUtils.info("Navigated to the base URL: " + getPropertyData("environments", "Base_URL"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().manage().window().maximize();
    }

    @Test
    public void CheckingTheNumberOfAddingAllProducts() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton().
                ClickAddToCartButtonForAll();

        Assert.assertTrue(new P02_ProductsPage(getDriver()).CompareSelectedItemsWithCart(),
                "The number of selected items does not match the number of items in the cart.");
    }

    @Test
    public void CheckingTheNumberOfItemsInCart() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton().
                ClickAddToCartButtonForRandomProd(
                        Integer.parseInt(getJsonData("ProductsData", "SelectedProducts")),
                        Integer.parseInt(getJsonData("ProductsData", "NumOfAllProducts")));

        Assert.assertTrue(new P02_ProductsPage(getDriver()).GetNumberOfItemsInCart() > 0,
                "The number of items in the cart is zero.");
    }

    @Test
    public void ClickOnCartIcon() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton().
                ClickCartIcon();

        Assert.assertTrue(new P02_ProductsPage(getDriver()).VerifyCartURL(getPropertyData("environments", "CARTURL")),
                "The cart URL is not correct.");
    }

    @AfterMethod
    public void TearDown() {
        quitDriver();
    }

}
