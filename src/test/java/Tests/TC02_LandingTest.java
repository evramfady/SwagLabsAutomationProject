package Tests;

import Listeners.IInvokedListener;
import Listeners.ITestListener;
import Pages.P01_LoginPage;
import Pages.P02_ProductsPage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static DriverFactory.DriverFactory.getDriver;
import static Utilities.DataUtils.getJsonData;
import static Utilities.DataUtils.getPropertyData;
import static org.assertj.core.api.Assertions.assertThat;

@Listeners({IInvokedListener.class, ITestListener.class})
public class TC02_LandingTest extends BaseTest {

    @Test
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

    @Test
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

    @Test
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