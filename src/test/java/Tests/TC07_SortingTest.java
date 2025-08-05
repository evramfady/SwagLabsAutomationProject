package Tests;

import Listeners.IInvokedListener;
import Listeners.ITestListener;
import Pages.P01_LoginPage;
import Pages.P02_ProductsPage;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static DriverFactory.DriverFactory.getDriver;
import static Utilities.DataUtils.getPropertyData;
import static org.assertj.core.api.Assertions.assertThat;

@Listeners({IInvokedListener.class, ITestListener.class})
public class TC07_SortingTest extends BaseTest {

    @BeforeMethod
    public void login() throws IOException {
        new P01_LoginPage(getDriver()).
                EnterUserName(getPropertyData("LogInData", "ValidUserName")).
                EnterPassword(getPropertyData("LogInData", "ValidPassword")).
                ClickLoginButton();
    }

    @Test(description = "Verify products can be sorted by name in ascending order")
    @Severity(SeverityLevel.NORMAL)
    public void SortProductsByNameAscending() {
        P02_ProductsPage productsPage = new P02_ProductsPage(getDriver());
        productsPage.SortProducts("Name (A to Z)");
        assertThat(productsPage.IsSortedByNameAscending())
                .withFailMessage("Products are not sorted by name in ascending order.")
                .isTrue();
    }

    @Test(description = "Verify products can be sorted by name in descending order")
    @Severity(SeverityLevel.NORMAL)
    public void SortProductsByNameDescending() {
        P02_ProductsPage productsPage = new P02_ProductsPage(getDriver());
        productsPage.SortProducts("Name (Z to A)");
        assertThat(productsPage.IsSortedByNameDescending())
                .withFailMessage("Products are not sorted by name in descending order.")
                .isTrue();
    }

    @Test(description = "Verify products can be sorted by price in ascending order")
    @Severity(SeverityLevel.NORMAL)
    public void SortProductsByPriceAscending() {
        P02_ProductsPage productsPage = new P02_ProductsPage(getDriver());
        productsPage.SortProducts("Price (low to high)");
        assertThat(productsPage.IsSortedByPriceAscending())
                .withFailMessage("Products are not sorted by price in ascending order.")
                .isTrue();
    }

    @Test(description = "Verify products can be sorted by price in descending order")
    @Severity(SeverityLevel.NORMAL)
    public void SortProductsByPriceDescending() {
        P02_ProductsPage productsPage = new P02_ProductsPage(getDriver());
        productsPage.SortProducts("Price (high to low)");
        assertThat(productsPage.IsSortedByPriceDescending())
                .withFailMessage("Products are not sorted by price in descending order.")
                .isTrue();
    }
}
