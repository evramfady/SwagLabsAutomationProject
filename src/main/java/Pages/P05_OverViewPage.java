package Pages;

import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverViewPage {
    private final WebDriver driver;

    private final By SubTotal = By.className("summary_subtotal_label");
    private final By Total = By.className("summary_total_label");
    private final By Tax = By.className("summary_tax_label");
    private final By FinishButton = By.xpath("//a[normalize-space()='FINISH']");

    public P05_OverViewPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean VerifyShipmentInfoPage() {
        try {
            String expectedURL = DataUtils.getPropertyData("environments", "ShipmentURL");
            String actualURL = Utility.getCurrentUrl(driver, expectedURL);
            return actualURL.contains(expectedURL);
        } catch (Exception e) {
            System.out.println("Error verifying shipment info page: " + e.getMessage());
            return false;
        }
    }

    @Step("Verifying the final total price")
    public boolean VerifyTotal() {
        float subTotalValue = Float.parseFloat(Utility.GetText(driver, SubTotal).replace("Item total: $", ""));
        float taxValue = Float.parseFloat(Utility.GetText(driver, Tax).replace("Tax: $", ""));
        float totalValue = Float.parseFloat(Utility.GetText(driver, Total).replace("Total: $", ""));
        float expectedTotal = subTotalValue + taxValue;

        if (Math.abs(totalValue - expectedTotal) < 0.001) {
            LogsUtils.info("The total value is correct: " + totalValue);
            return true;
        } else {
            LogsUtils.error("Total value mismatch. Expected: " + expectedTotal + ", but got: " + totalValue);
            return false;
        }
    }

    @Step("Clicking the finish button")
    public P06_ClickFinishButton ClickFinishButton() {
        Utility.ClickOnElement(driver, FinishButton);
        LogsUtils.info("Clicked on the Finish button successfully.");
        return new P06_ClickFinishButton(driver);
    }
}