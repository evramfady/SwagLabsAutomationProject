package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartPage {

    private final By PricePerItem = By.xpath("//div[@class='inventory_item_price']");
    private final By CheckOutButton = By.cssSelector(".btn_action.checkout_button");
    private final WebDriver driver;

    public P03_CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean ComparingTotalPrices(float price) {
        return (GetTotalPriceFromCart() == price);
    }

    @Step("Get total price from cart page")
    public float GetTotalPriceFromCart() {
        // GOOD: totalPrice is a local variable, not a problematic static field
        float totalPrice = 0.0f;
        try {
            List<WebElement> priceElements = driver.findElements(PricePerItem);
            for (WebElement priceElement : priceElements) {
                String itemPrice = priceElement.getText().replace("$", "").trim();
                totalPrice += Float.parseFloat(itemPrice);
            }
            LogsUtils.info("Total price of items calculated in the cart page: " + totalPrice);
            return totalPrice;
        } catch (Exception e) {
            LogsUtils.error("Error parsing price in cart: " + e.getMessage());
            return 0;
        }
    }

    @Step("Clicking on the checkout button")
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
