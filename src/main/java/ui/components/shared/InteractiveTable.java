package ui.components.shared;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ui.extension.Driver;

import java.util.List;
import java.util.stream.IntStream;

public class InteractiveTable {
    private final By parent;

    By tableHeaders = By.xpath(".//thead//th");
    By tableRows = By.xpath(".//tbody//tr");
    By tableCell = By.xpath(".//td");

    public InteractiveTable(By parent) {
        this.parent = parent;
    }

    public int getIndexForColumn(String columnName) {
        List<WebElement> headers = Driver.get().findElement(parent)
                .findElements(tableHeaders);

        return IntStream.range(0, headers.size())
                .filter(i -> headers.get(i).getText().equals(columnName))
                .findFirst().orElseThrow();
    }

    public void waitForComponentIsLoaded() {
        Driver.getWait().until(d -> d.findElement(parent)
                .findElements(tableRows).size() > 1);
    }

    public int getRowsCount() {
        return Driver.get().findElement(parent)
                .findElements(tableRows).size();
    }

    public String getRowColumnValue(int rowIndex, int columnIndex) {
        return Driver.get().findElement(parent)
                .findElements(tableRows).get(rowIndex)
                .findElements(tableCell).get(columnIndex)
                .getText();
    }
}
