package ui.extension;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import ui.config.Environment;

import java.net.URL;

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
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--headless=new");
            return new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        } catch (Exception ignored) { }
        return null;
    }
}
