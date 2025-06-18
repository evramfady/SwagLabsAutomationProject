package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartPage {

    private static final By PricePerItem = By.xpath("//button[.='REMOVE']//preceding-sibling::div[@class='inventory_item_price']");
    private static float TotalPrice = 0.0f;
    private final By CheckOutButton = By.xpath("//a[@class='btn_action checkout_button']");
    private final WebDriver driver;

    public P03_CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean ComparingTotalPrices(float price) {
        return (GetTotalPriceFormCart() == price);
    }

    public float GetTotalPriceFormCart() {
        try {
            List<WebElement> priceElements = driver.findElements(PricePerItem);
            for (int i = 1; i <= priceElements.size(); i++) {

                LogsUtils.info("Price of item " + i + ": " + priceElements.get(i - 1).getText());
                By PriceElement = By.xpath("(//button[.='REMOVE']//preceding-sibling::div[@class='inventory_item_price'])[" + i + "]");
                String ItemPrice = Utility.GetText(driver, PriceElement).replace("$", "").trim();
                TotalPrice += Float.parseFloat(ItemPrice);
            }
            LogsUtils.info("Total price of items in the cart: " + TotalPrice);
            return TotalPrice;
        } catch (Exception e) {
            LogsUtils.error("Error parsing price: " + e.getMessage());
            return 0;
        }
    }

    public P04_CheckOutPage ClickOnCheckoutPage() {
        try {
            Utility.ClickOnElement(driver, CheckOutButton);
            LogsUtils.info("Clicked on the checkout button.");
        } catch (Exception e) {
            LogsUtils.error("Error clicking on the checkout button: " + e.getMessage());
        }
        return new P04_CheckOutPage(driver);
    }
}
