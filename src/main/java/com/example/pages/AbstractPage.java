package com.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractPage {
    static final Logger log = getLogger(lookup().lookupClass());
    protected final WebDriver driver;
    /*
    We define an explicit wait (WebDriverWait) attribute in the base class.
    We instantiate this attribute in the constructor using a default timeout value (five seconds in this example).
     */
    protected  WebDriverWait webDriverWait;
    int timeoutSec = 20; // wait timeout (20 seconds by default)

    public AbstractPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    /*
     We create a setter method to change the default value for the wait timeout.
     For instance, we might need to adjust this timeout depending on the system response time.
     */
    public void setTimeoutSec(int timeoutSec) {
        this.timeoutSec = timeoutSec;
        // If the webDriverWait object has already been created, set it to null to recreate
        this.webDriverWait = null;
    }
    /*
   We create several common methods that page classes can reuse, such as
   visit() (to open a web page), find() (to locate a web element),
   or type() (to send data to a writable element, such as an input field).
    */
    public void visit(String url){driver.get(url);}
    protected WebElement findElement(final By by) {
        return getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected List<WebElement> findElements(final By by) {
        try {
            return getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        } catch (TimeoutException ignored) {
            return new ArrayList<>(0);
        }
    }
    public void clickOnElement(By by) {
        clickOnElement(findElement(by));
    }

    protected void clickOnElement(final WebElement element) {
        try {
            waitUntilClickable(element).click();
        } catch (WebDriverException e) {
            log.warn("WebDriverException caught, trying to scroll and click: " + e.getMessage());
            scrollToElementAndClick(element);
        }
    }
    public void type(WebElement element, String text) {
        waitUntilClickable(element);
        element.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), text);
    }

    public void type(By locator, String text) {
        WebElement element = findElement(locator);
        type(element, text);
    }

    public boolean isDisplayed(WebElement element) {
        return isDisplayed(ExpectedConditions.visibilityOf(element));
    }

    public boolean isDisplayed(By locator) {
        return isDisplayed(
                ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public boolean isDisplayed(ExpectedCondition<?> expectedCondition) {
        try {
            webDriverWait.until(expectedCondition);
        } catch (TimeoutException e) {
            log.warn("Timeout of {} wait for element ", timeoutSec);
            return false;
        }
        return true;
    }
    protected WebDriverWait getWebDriverWait() {
        if (webDriverWait == null) {
            webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
        }
        return webDriverWait;
    }

    protected void scrollToElementAndClick(WebElement element) {
        scrollToElement(element);
        element.click();
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    protected WebElement waitUntilClickable(final WebElement element) {
        return getWebDriverWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitUntilVisible(final WebElement element) {
        return getWebDriverWait().until(ExpectedConditions.visibilityOf(element));
    }
}
/*
    These methods abstract the underlying Selenium WebDriver details,
    making the test scripts more about the business logic
    and user interactions rather than the technical details of Selenium commands.
 */

