package Pages;

import Utilities.DataUtils;
import Utilities.Utility;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class P06_ClickFinishButton {

    private final WebDriver driver;

    public P06_ClickFinishButton(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Verifying the finish page URL")
    public boolean VerifyFinishPage() {
        try {
            String expectedURL = DataUtils.getPropertyData("environments", "FinishURL");
            String actualURL = Utility.getCurrentUrl(driver, expectedURL);
            return actualURL.contains(expectedURL);
        } catch (Exception e) {
            System.out.println("Error verifying finish page: " + e.getMessage());
            return false;
        }
    }
}
