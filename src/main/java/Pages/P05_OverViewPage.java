package Pages;

import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverViewPage {
    private final WebDriver driver;

    private final By SubTotal = By.xpath("//div[@class='summary_subtotal_label']");
    private final By Total = By.xpath("//div[@class='summary_total_label']");
    private final By Tax = By.xpath("//div[@class='summary_tax_label']");
    private final By FinishButton = By.xpath("//a[@class='btn_action cart_button']");

    public P05_OverViewPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean VerifyShipmentInfoPage() {
        try {
            // Assuming the shipment info page has a specific title or element that can be verified
            String ActualURL = Utility.getCurrentUrl(driver,
                    DataUtils.getPropertyData("environments", "ShipmentURL"));
            return ActualURL.contains(DataUtils.getPropertyData("environments", "ShipmentURL"));
        } catch (Exception e) {
            System.out.println("Error verifying shipment info page: " + e.getMessage());
            return false;
        }
    }

    public boolean VerifyTotal() {
        float SubTotalValue = Float.parseFloat(Utility.GetText(driver, SubTotal).replace("Item total: $", ""));
        float TaxValue = Float.parseFloat(Utility.GetText(driver, Tax).replace("Tax: $", ""));
        float TotalValue = Float.parseFloat(Utility.GetText(driver, Total).replace("Total: $", ""));
        float ExpectedTotal = SubTotalValue + TaxValue;
        if (TotalValue == ExpectedTotal) {
            LogsUtils.info("The total value is correct: " + TotalValue);
            return true;
        } else {
            Exception e = new Exception("Total value mismatch");
            LogsUtils.error("The total value is incorrect. Expected: " + ExpectedTotal + ", but got: " + TotalValue + e.getMessage());
            return false;
        }
        
    }

    public P06_ClickFinishButton ClickFinishButton() {
        Utility.ClickOnElement(driver, FinishButton);
        LogsUtils.info("Clicked on the Finish button successfully.");
        return new P06_ClickFinishButton(driver);
    }
}
