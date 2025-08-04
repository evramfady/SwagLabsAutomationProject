package Tests;

import Listeners.IInvokedListener;
import Listeners.ITestListener;
import Pages.P01_LoginPage;
import Pages.P02_ProductsPage;
import Pages.P03_CartPage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static DriverFactory.DriverFactory.getDriver;
import static Utilities.DataUtils.getJsonData;
import static Utilities.DataUtils.getPropertyData;
import static org.assertj.core.api.Assertions.assertThat;

@Listeners({IInvokedListener.class, ITestListener.class})
public class TC03_CartTest extends BaseTest {

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
        assertThat(new P03_CartPage(getDriver()).ComparingTotalPrices(TotalPrice))
                .withFailMessage("The total price of items in the cart does not match the expected total price.")
                .isTrue();
    }
}