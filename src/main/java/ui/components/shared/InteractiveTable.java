package ui.components.shared;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

public class InteractiveTable {
    private final By parent;

    private WebDriver driver;
    private final WebDriverWait wait;

    By tableHeaders = By.xpath(".//thead//th");
    By tableRows = By.xpath(".//tbody//tr");
    By tableCell = By.xpath(".//td");

    public InteractiveTable(WebDriver driver, By parent) {
        this.parent = parent;
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public int getIndexForColumn(String columnName) {
        List<WebElement> headers = driver.findElement(parent)
                .findElements(tableHeaders);

        return IntStream.range(0, headers.size())
                .filter(i -> headers.get(i).getText().equals(columnName))
                .findFirst().orElseThrow();
    }

    public void waitForComponentIsLoaded() {
        wait.until(d -> d.findElement(parent)
                .findElements(tableRows).size() > 1);
    }

    public int getRowsCount() {
        return driver.findElement(parent)
                .findElements(tableRows).size();
    }

    public String getRowColumnValue(int rowIndex, int columnIndex) {
        return driver.findElement(parent)
                .findElements(tableRows).get(rowIndex)
                .findElements(tableCell).get(columnIndex)
                .getText();
    }
}
