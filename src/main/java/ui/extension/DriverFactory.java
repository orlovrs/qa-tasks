package ui.extension;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ui.config.Environment;

public class DriverFactory {
    static WebDriver get() {
        switch (Environment.BROWSER) {
            case "edge": return getEdge();
            case "firefox": return getFirefox();
            default: return getChrome();
        }
    }

    static WebDriver getEdge() {
        WebDriverManager.chromedriver().setup();
        return new EdgeDriver();
    }

    static WebDriver getFirefox() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }

    static WebDriver getChrome() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
}
