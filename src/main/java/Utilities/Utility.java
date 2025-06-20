package Utilities;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.Set;

public class Utility {

    private static final String SCREENSHOTSPATH = "testOutputs/ScreenShots/";

    public static void ClickOnElement(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(locator));
            FindWebElement(driver, locator).click();
        } catch (Exception e) {
            System.out.println("Error clicking on element: " + e.getMessage());
        }
    }

    public static void SendData(WebDriver driver, By locator, String data) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            FindWebElement(driver, locator).sendKeys(data);
        } catch (Exception e) {
            System.out.println("Error typing on element: " + e.getMessage());
        }
    }

    public static String GetText(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("Error getting text from element: " + e.getMessage());
        }
        return FindWebElement(driver, locator).getText();
    }

    public static WebDriverWait GeneralWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static void ScrollToElementJS(WebDriver driver, By locator) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
                    FindWebElement(driver, locator));
        } catch (Exception e) {
            System.out.println("Error scrolling to element: " + e.getMessage());
        }
    }

    public static WebElement FindWebElement(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }

    public static String getTimeDateStamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ssa").format(new Date());
    }

    public static void TakeScreenshot(WebDriver driver, String ScreenshotName) {
        try {
            File ScreenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File ScreenshotDes = new File(SCREENSHOTSPATH + ScreenshotName + "-" + getTimeDateStamp() + ".png");
            FileUtils.copyFile(ScreenshotSrc, ScreenshotDes);

            Allure.addAttachment(ScreenshotName, Files.newInputStream(Path.of(ScreenshotDes.getPath())));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SelectingFromDropdown(WebDriver driver, By locator, String value) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(locator));
            new Select(FindWebElement(driver, locator)).selectByVisibleText(value);
        } catch (Exception e) {
            System.out.println("Error selecting from dropdown: " + e.getMessage());
        }
    }

    public static void SelectingFromDropdown(WebDriver driver, By locator, int value) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(locator));
            new Select(FindWebElement(driver, locator)).selectByIndex(value);
        } catch (Exception e) {
            System.out.println("Error selecting from dropdown: " + e.getMessage());
        }
    }

    public static void ShutterBugScreenShot(WebDriver driver, By locator) {
        try {
            Shutterbug.shootPage(driver, Capture.FULL_SCROLL)
                    .highlight(FindWebElement(driver, locator))
                    .save(SCREENSHOTSPATH);

        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
        }
    }

    public static int GenerateRandomNumber(int max) {
        return new Random().nextInt(max) + 1;
    }

    public static Set<Integer> GenerateRandomSet(int size, int max) {
        Set<Integer> randomSet = new java.util.HashSet<>();
        while (randomSet.size() < size) {
            randomSet.add(GenerateRandomNumber(max));
        }
        return randomSet;
    }

    public static String getCurrentUrl(WebDriver driver, String ExpectedURL) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.urlToBe(ExpectedURL));
        } catch (Exception e) {
            LogsUtils.info(e.getMessage());
        }
        return driver.getCurrentUrl();
    }


    public static Set<Cookie> getAllCookies(WebDriver driver) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        } catch (Exception e) {
            LogsUtils.error("Error getting cookies: " + e.getMessage());
        }
        return driver.manage().getCookies();
    }

    public static void AddCookie(WebDriver driver, Set<Cookie> cookies) {
        try {
            for (Cookie cookie : cookies) {
                LogsUtils.info("Adding cookie: " + cookie.getName() + " = " + cookie.getValue());
                driver.manage().addCookie(cookie);
            }
        } catch (Exception e) {
            LogsUtils.error("Error adding cookie: " + e.getMessage());
        }
    }

    public static String SelectingBrowser() throws IOException {
        return System.getProperty("browser") != null ? System.getProperty("browser") : DataUtils.getPropertyData("environments", "Browser");
    }

}
