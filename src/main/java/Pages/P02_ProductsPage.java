package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static Utilities.Utility.GenerateRandomSet;
import static Utilities.Utility.GetText;

public class P02_ProductsPage {

    private static final By PricePerSelectedItem = By.xpath("//button[.='REMOVE']//preceding-sibling::div[@class='inventory_item_price']");
    private final By CartIcon = By.className("shopping_cart_link");
    private final By NumberOfItemsInCartIcon = By.xpath("//span[contains(@class,'shopping_cart_badge')]");
    private final By AddToCartButtonForAll = By.xpath("//button[contains(@class,'btn_inventory')]");
    private final By NumberOfSelectedItems = By.xpath("//button[.='REMOVE']");
    private final By PricePerItem = By.xpath("//button[.='REMOVE']//preceding-sibling::div[@class='inventory_item_price']");
    private final By productSortContainer = By.className("product_sort_container");
    private final By inventoryItemName = By.className("inventory_item_name");
    private final By inventoryItemPrice = By.className("inventory_item_price");
    private final WebDriver driver;

    public P02_ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public By GetCartItemsPage() {
        return NumberOfItemsInCartIcon;
    }

    @Step("Adding all available products to the cart")
    public P02_ProductsPage ClickAddToCartButtonForAll() {
        List<WebElement> AllProducts = driver.findElements(AddToCartButtonForAll);
        LogsUtils.info("Number of products found: " + AllProducts.size());
        for (int i = 1; i <= AllProducts.size(); i++) {
            WebElement addToCartButton = driver.findElement(By.xpath("(//button[contains(@class,'btn_inventory')])[" + i + "]"));
            if (addToCartButton.isDisplayed() && addToCartButton.isEnabled()) {
                addToCartButton.click();
            }
        }
        return this;
    }

    public int GetNumberOfItemsInCart() {
        try {
            LogsUtils.info("Getting the number of items in the cart icon." + Utility.GetText(driver, NumberOfItemsInCartIcon));
            return Integer.parseInt(GetText(driver, NumberOfItemsInCartIcon));
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return 0;
        }
    }

    public int GetNumberOfSelectedItems() {
        try {
            List<WebElement> selectedItems = driver.findElements(NumberOfSelectedItems);
            LogsUtils.info("Number of selected items: " + selectedItems.size());
            return selectedItems.size();
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return 0;
        }
    }

    public boolean CompareSelectedItemsWithCart() {
        try {
            return GetNumberOfSelectedItems() == GetNumberOfItemsInCart();
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return false;
        }
    }

    @Step("Adding {size} random products to the cart")
    public P02_ProductsPage ClickAddToCartButtonForRandomProd(int size, int max) {
        Set<Integer> RandomNumbers = GenerateRandomSet(size, max);
        for (int i : RandomNumbers) {
            LogsUtils.info("Adding product number: " + i);
            WebElement addToCartButton = driver.findElement(By.xpath("(//button[contains(@class,'btn_inventory')])[" + i + "]"));
            if (addToCartButton.isDisplayed() && addToCartButton.isEnabled()) {
                addToCartButton.click();
            }
        }
        return this;
    }

    @Step("Clicking on the cart icon")
    public P03_CartPage ClickCartIcon() {
        try {
            Utility.ClickOnElement(driver, CartIcon);
            LogsUtils.info("Clicked on the cart icon.");
        } catch (Exception e) {
            LogsUtils.error("Error clicking on the cart icon: " + e.getMessage());
        }
        return new P03_CartPage(driver);
    }

    public boolean VerifyCartURL(String expectedURL) {
        try {
            String currentURL = Utility.getCurrentUrl(driver, expectedURL);
            LogsUtils.info("Current URL: " + currentURL);
            return currentURL.equals(expectedURL);
        } catch (Exception e) {
            LogsUtils.error("Error verifying cart URL: " + e.getMessage());
            return false;
        }
    }

    public float GetTotalPriceForItems() {
        float TotalPrice = 0.0f;
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

    @Step("Sorting products by '{option}'")
    public void SortProducts(String option) {
        Select dropdown = new Select(driver.findElement(productSortContainer));
        dropdown.selectByVisibleText(option);
    }

    public boolean IsSortedByNameAscending() {
        List<WebElement> productNames = driver.findElements(inventoryItemName);
        List<String> actualNames = new ArrayList<>();
        for (WebElement productName : productNames) {
            actualNames.add(productName.getText());
        }
        List<String> sortedNames = new ArrayList<>(actualNames);
        Collections.sort(sortedNames);
        return actualNames.equals(sortedNames);
    }

    public boolean IsSortedByNameDescending() {
        List<WebElement> productNames = driver.findElements(inventoryItemName);
        List<String> actualNames = new ArrayList<>();
        for (WebElement productName : productNames) {
            actualNames.add(productName.getText());
        }
        List<String> sortedNames = new ArrayList<>(actualNames);
        Collections.sort(sortedNames, Collections.reverseOrder());
        return actualNames.equals(sortedNames);
    }

    public boolean IsSortedByPriceAscending() {
        List<WebElement> productPrices = driver.findElements(inventoryItemPrice);
        List<Float> actualPrices = new ArrayList<>();
        for (WebElement productPrice : productPrices) {
            actualPrices.add(Float.parseFloat(productPrice.getText().replace("$", "")));
        }
        List<Float> sortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(sortedPrices);
        return actualPrices.equals(sortedPrices);
    }

    public boolean IsSortedByPriceDescending() {
        List<WebElement> productPrices = driver.findElements(inventoryItemPrice);
        List<Float> actualPrices = new ArrayList<>();
        for (WebElement productPrice : productPrices) {
            actualPrices.add(Float.parseFloat(productPrice.getText().replace("$", "")));
        }
        List<Float> sortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(sortedPrices, Collections.reverseOrder());
        return actualPrices.equals(sortedPrices);
    }
}