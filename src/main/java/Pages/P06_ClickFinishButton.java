package Pages;

import Utilities.DataUtils;
import Utilities.Utility;
import org.openqa.selenium.WebDriver;

public class P06_ClickFinishButton {

    private final WebDriver driver;

    public P06_ClickFinishButton(WebDriver driver) {
        this.driver = driver;
    }

    public boolean VerifyFinishPage() {
        try {
            String ActualURL = Utility.getCurrentUrl(driver,
                    DataUtils.getPropertyData("environments", "FinishURL"));
            return ActualURL.contains(DataUtils.getPropertyData("environments", "FinishURL"));
        } catch (Exception e) {
            System.out.println("Error verifying shipment info page: " + e.getMessage());
            return false;
        }
    }
}
