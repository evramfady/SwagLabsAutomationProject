package Tests;

import Listeners.IInvokedListener;
import Listeners.ITestListener;
import Pages.P01_LoginPage;
import Pages.P02_ProductsPage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static DriverFactory.DriverFactory.getDriver;
import static Utilities.DataUtils.getPropertyData;
import static org.assertj.core.api.Assertions.assertThat;

@Listeners({IInvokedListener.class, ITestListener.class})
public class TC07_SortingTest extends BaseTest {

    @Test
    public void SortProductsByNameAscending() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton();
        new P02_ProductsPage(getDriver()).SortProducts("Name (A to Z)");
        assertThat(new P02_ProductsPage(getDriver()).IsSortedByNameAscending())
                .withFailMessage("Products are not sorted by name in ascending order.")
                .isTrue();
    }

    @Test
    public void SortProductsByNameDescending() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton();
        new P02_ProductsPage(getDriver()).SortProducts("Name (Z to A)");
        assertThat(new P02_ProductsPage(getDriver()).IsSortedByNameDescending())
                .withFailMessage("Products are not sorted by name in descending order.")
                .isTrue();
    }

    @Test
    public void SortProductsByPriceAscending() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton();
        new P02_ProductsPage(getDriver()).SortProducts("Price (low to high)");
        assertThat(new P02_ProductsPage(getDriver()).IsSortedByPriceAscending())
                .withFailMessage("Products are not sorted by price in ascending order.")
                .isTrue();
    }

    @Test
    public void SortProductsByPriceDescending() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton();
        new P02_ProductsPage(getDriver()).SortProducts("Price (high to low)");
        assertThat(new P02_ProductsPage(getDriver()).IsSortedByPriceDescending())
                .withFailMessage("Products are not sorted by price in descending order.")
                .isTrue();
    }
}
