package Tests;

import Listeners.IInvokedListener;
import Listeners.ITestListener;
import Pages.P06_ClickFinishButton;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static DriverFactory.DriverFactory.getDriver;
import static Utilities.DataUtils.getJsonData;
import static Utilities.DataUtils.getPropertyData;
import static org.assertj.core.api.Assertions.assertThat;

@Listeners({IInvokedListener.class, ITestListener.class})
public class TC06_FinishPageTest extends BaseTest {

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
        //TODO: Click the Finish button
        new Pages.P05_OverViewPage(getDriver())
                .ClickFinishButton()
                .VerifyFinishPage();

        assertThat(new P06_ClickFinishButton(getDriver()).VerifyFinishPage())
                .withFailMessage("The finish page is not displayed as expected.")
                .isTrue();
    }
}