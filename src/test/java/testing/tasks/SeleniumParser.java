package testing.tasks;

import com.opencsv.CSVWriter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import ui.pages.HomePage;
import ui.pages.PreOpenMarketPage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;

/*
Парсер данных через селениум на сайте https://www.nseindia.com/
Алгоритм:
1.	Зайти па https://www.nseindia.соm
2.	Навестись (hover) на MARKET DATA
3.	Кликнуть на Pre-Open Market
4.	Спарсить данные Final Price по всем позициям на странице и вывести их в csv файл: Имя;цена

После этого сымитироватьнебольшой пользовательский сценарий использования сайта.
Здесь по своему желанию, по как пример:
1.	Зайти па главную страницу
2.	Пролистать вниз до графика
3.	Выбрать график "NIFTY BANK"
4.	Нажать ‘View all” под 'TOP 5 STOCKS - NIFTY BANK"
5.	Выбрать в селекторе “NIFTY ALPHA 50”
6.	Пролистать таблицу до конца
 */
public class SeleniumParser {
    private ChromeDriver driver;

    private HomePage home;
    private PreOpenMarketPage preOpenMarket;

    @BeforeAll
    public static void globalSetup() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        home = new HomePage(driver);
        preOpenMarket = new PreOpenMarketPage(driver);
        home.open();
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    public void parsing() throws IOException {
        home.clickPreOpenMarketVanBarLink();
        Map<String, Double> prices = preOpenMarket.getPrices();

        File file = new File("test.csv");
        FileWriter outputfile = new FileWriter(file);

        CSVWriter writer = new CSVWriter(outputfile, ';');

        String[] header = {"Имя", "цена"};
        writer.writeNext(header);

        for (Map.Entry<String, Double> entry : prices.entrySet()) {
            writer.writeNext(new String[] {entry.getKey(), entry.getValue().toString()});
        }

        writer.close();
    }
}