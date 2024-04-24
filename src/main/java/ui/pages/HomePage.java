package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ui.extension.Driver;

public class HomePage extends BasePage {

    By navigationBar = By.id("main_navbar");
    By marketDataItem = By.xpath(".//a[contains(text(), 'Market Data')]/ancestor::li");
    By preOpenMarketLink = By.xpath(".//a[contains(text(), 'Pre-Open Market')]");

    public HomePage() {
        super();
        this.url = "/";
    }

    public void clickPreOpenMarketVanBarLink() {
        Actions action = new Actions(Driver.get());
        WebElement element = Driver.get().findElement(navigationBar)
                .findElement(marketDataItem);
        action.moveToElement(element)
                .build()
                .perform();

        Driver.get().findElement(navigationBar)
                .findElement(marketDataItem)
                .findElement(preOpenMarketLink)
                .click();
    }
}
