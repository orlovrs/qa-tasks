package ui.extension;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class Screenshotter implements AfterTestExecutionCallback {
    private static WebDriver driver;

    public static void setDriver(WebDriver driver) {
        Screenshotter.driver = driver;
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        if (extensionContext.getExecutionException().isPresent()) {
            String baseFileName = extensionContext.getRequiredTestClass().getSimpleName() + "-"
                    + extensionContext.getRequiredTestMethod().getName()
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("-yyMMdd-HHmmss"));
            File targetFile = new File("screenshots/" + baseFileName + ".png");
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(scrFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            targetFile.setReadable(true, false);
        }
    }
}
