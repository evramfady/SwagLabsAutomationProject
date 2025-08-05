package Tests;

import Listeners.IInvokedListener;
import Listeners.ITestListener;
import Pages.P01_LoginPage;
import Pages.P05_OverViewPage;
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
public class TC04_CheckOutPage extends BaseTest {

    @Test(description = "Verify successful navigation to the overview page after filling checkout form")
    @Severity(SeverityLevel.CRITICAL)
    public void VerifyFillingFormAndGotoOverviewpage() throws IOException {
        new P01_LoginPage(getDriver())
                .EnterUserName(getPropertyData("LogInData", "ValidUserName"))
                .EnterPassword(getPropertyData("LogInData", "ValidPassword"))
                .ClickLoginButton()
                .ClickAddToCartButtonForRandomProd(
                        Integer.parseInt(getJsonData("ProductsData", "SelectedProducts")),
                        Integer.parseInt(getJsonData("ProductsData", "NumOfAllProducts")))
                .ClickCartIcon()
                .ClickOnCheckoutPage()
                .FillingCheckOutForm()
                .ClickContinueButton();

        assertThat(new P05_OverViewPage(getDriver()).VerifyShipmentInfoPage())
                .withFailMessage("The shipment information page is not displayed as expected.")
                .isTrue();
    }
}