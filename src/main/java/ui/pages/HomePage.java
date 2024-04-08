package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class HomePage extends BasePage {

    By navigationBar = By.id("main_navbar");
    By marketDataItem = By.xpath(".//a[contains(text(), 'Market Data')]/ancestor::li");
    By preOpenMarketLink = By.xpath(".//a[contains(text(), 'Pre-Open Market')]");

    public HomePage(WebDriver driver) {
        super(driver);
        this.url = "/";
    }

    public void clickPreOpenMarketVanBarLink() {
        Actions action = new Actions(this.driver);
        WebElement element = this.driver.findElement(navigationBar)
                .findElement(marketDataItem);
        action.moveToElement(element)
                .build()
                .perform();

        this.driver.findElement(navigationBar)
                .findElement(marketDataItem)
                .findElement(preOpenMarketLink)
                .click();
    }
}
