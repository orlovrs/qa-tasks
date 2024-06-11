package ui.extension;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;

public class Driver {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    public static WebDriver get() {
        if (driver.get() == null) {
            driver.set(DriverFactory.get());
            wait.set(new WebDriverWait(driver.get(), Duration.ofSeconds(10)));
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driver.get();
    }

    public static void quit() {
        driver.get().quit();
        driver.remove();
    }

    public static WebDriverWait getWait() {
        return wait.get();
    }
}
