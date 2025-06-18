package Tests;

import Listeners.IInvokedListener;
import Listeners.ITestListener;
import Pages.P01_LoginPage;
import Pages.P02_ProductsPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getJsonData;
import static Utilities.DataUtils.getPropertyData;
import static Utilities.Utility.AddCookie;
import static Utilities.Utility.getAllCookies;


@Listeners({IInvokedListener.class, ITestListener.class})
public class TC02_LandingTestWithCookies {

    private static final Logger log = LoggerFactory.getLogger(TC02_LandingTestWithCookies.class);
    private Set<Cookie> cookies;


    @BeforeClass(alwaysRun = true)
    public void LoginAndSetUpClass() {
        try {
            SetUpDriver(Utility.SelectingBrowser());
            LogsUtils.info(Utility.SelectingBrowser() + " Driver is set up successfully");
            getDriver().get(getPropertyData("environments", "Base_URL"));
            LogsUtils.info("Navigated to the base URL: " + getPropertyData("environments", "Base_URL"));
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            getDriver().manage().window().maximize();
            new P01_LoginPage(getDriver()).
                    EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                    EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                    ClickLoginButton();
            LogsUtils.info("User logged in successfully");
            cookies = getAllCookies(getDriver());
            quitDriver();
        } catch (IOException e) {
            LogsUtils.error("Error during setup: " + e.getMessage());
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void SetUp() throws IOException {
        SetUpDriver(Utility.SelectingBrowser());
        LogsUtils.info(Utility.SelectingBrowser() + " Driver is set up successfully");
        getDriver().get(getPropertyData("environments", "Base_URL"));
        LogsUtils.info("Navigated to the base URL: " + getPropertyData("environments", "Base_URL"));
        AddCookie(getDriver(), cookies);
        getDriver().get(DataUtils.getPropertyData("environments", "HomePageURL"));
        getDriver().navigate().refresh();
    }

    @Test
    public void CheckingTheNumberOfAddingAllProducts() throws IOException {

        new P02_ProductsPage(getDriver()).ClickAddToCartButtonForAll();

        Assert.assertTrue(new P02_ProductsPage(getDriver()).CompareSelectedItemsWithCart(),
                "The number of selected items does not match the number of items in the cart.");
    }

    @Test
    public void CheckingTheNumberOfItemsInCart() throws IOException {

        new P02_ProductsPage(getDriver()).ClickAddToCartButtonForRandomProd(
                Integer.parseInt(getJsonData("ProductsData", "SelectedProducts")),
                Integer.parseInt(getJsonData("ProductsData", "NumOfAllProducts")));

        Assert.assertTrue(new P02_ProductsPage(getDriver()).GetNumberOfItemsInCart() > 0,
                "The number of items in the cart is zero.");
    }

    @Test
    public void ClickOnCartIcon() throws IOException {
        new P02_ProductsPage(getDriver()).
                ClickCartIcon();

        Assert.assertTrue(new P02_ProductsPage(getDriver()).VerifyCartURL(getPropertyData("environments", "CARTURL")),
                "The cart URL is not correct.");
    }

    @AfterMethod(alwaysRun = true)
    public void TearDown() {
        quitDriver();
    }

    @AfterClass(alwaysRun = true)
    public void TearDownClass() {
        cookies.clear();
    }

}
