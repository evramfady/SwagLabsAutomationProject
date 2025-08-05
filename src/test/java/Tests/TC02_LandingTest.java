package Tests;

import Listeners.IInvokedListener;
import Listeners.ITestListener;
import Pages.P01_LoginPage;
import Pages.P02_ProductsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static DriverFactory.DriverFactory.getDriver;
import static Utilities.DataUtils.getJsonData;
import static Utilities.DataUtils.getPropertyData;
import static org.assertj.core.api.Assertions.assertThat;

@Listeners({IInvokedListener.class, ITestListener.class})
public class TC02_LandingTest extends BaseTest {

    @Test(description = "Verify that adding all products updates the cart icon correctly")
    @Description("Adds all products to the cart and verifies the cart badge number matches the number of products added.")
    @Severity(SeverityLevel.NORMAL)
    public void CheckingTheNumberOfAddingAllProducts() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton().
                ClickAddToCartButtonForAll();

        assertThat(new P02_ProductsPage(getDriver()).CompareSelectedItemsWithCart())
                .withFailMessage("The number of selected items does not match the number of items in the cart.")
                .isTrue();
    }

    @Test(description = "Verify the cart icon updates when adding a random number of products")
    @Description("Adds a random selection of products and verifies the cart icon shows a number greater than zero.")
    @Severity(SeverityLevel.NORMAL)
    public void CheckingTheNumberOfItemsInCart() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton().
                ClickAddToCartButtonForRandomProd(
                        Integer.parseInt(getJsonData("ProductsData", "SelectedProducts")),
                        Integer.parseInt(getJsonData("ProductsData", "NumOfAllProducts")));

        assertThat(new P02_ProductsPage(getDriver()).GetNumberOfItemsInCart())
                .withFailMessage("The number of items in the cart is zero.")
                .isGreaterThan(0);
    }

    @Test(description = "Verify clicking the cart icon navigates to the cart page")
    @Severity(SeverityLevel.NORMAL)
    public void ClickOnCartIcon() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton().
                ClickCartIcon();

        assertThat(new P02_ProductsPage(getDriver()).VerifyCartURL(getPropertyData("environments", "CARTURL")))
                .withFailMessage("The cart URL is not correct.")
                .isTrue();
    }
}