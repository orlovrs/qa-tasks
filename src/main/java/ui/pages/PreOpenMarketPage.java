package ui.pages;

import org.openqa.selenium.By;
import ui.components.shared.InteractiveTable;

import java.util.HashMap;
import java.util.Map;

public class PreOpenMarketPage extends BasePage {
    By table = By.id("livePreTable");

    InteractiveTable stocksTable;

    public PreOpenMarketPage() {
        super();
        this.url = "/market-data/pre-open-market-cm-and-emerge-market";
        this.stocksTable = new InteractiveTable(table);
    }

    public Map<String, Double> getPrices() {
        int symbolIndex = stocksTable.getIndexForColumn("SYMBOL");
        int priceIndex = stocksTable.getIndexForColumn("FINAL");

        stocksTable.waitForComponentIsLoaded();
        int rowsCount = stocksTable.getRowsCount();

        Map<String, Double> result = new HashMap<>();

        for (int i = 0; i < rowsCount - 1; i++) {
            String symbol = stocksTable.getRowColumnValue(i, symbolIndex);
            Double price = Double.parseDouble(
                    stocksTable.getRowColumnValue(i, priceIndex).replace(",", ""));
            result.put(symbol, price);
        }

        return result;
    }
}
