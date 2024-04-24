package ui.pages;

import ui.extension.Driver;

public class BasePage {
    private final String baseUrl = "https://www.nseindia.com";
    protected String url;

    protected BasePage() {}

    public void open() {
        Driver.get().get(
                String.format("%s%s", baseUrl, url)
        );
    }
}
