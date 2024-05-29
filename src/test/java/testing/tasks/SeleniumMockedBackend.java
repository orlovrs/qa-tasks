package testing.tasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.MediaType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.Filter;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import ui.extension.Driver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class SeleniumMockedBackend {
    @BeforeEach
    public void setup() throws InterruptedException, JsonProcessingException {
        CopyOnWriteArrayList<String> contentType = new CopyOnWriteArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> book = new HashMap<>() {{
            put("bookId", 3);
            put("title", "Harry Potter and the Chamber of Secrets");
            put("author", "JKR");
            put("category", "Mystery");
            put("price", 335.00);
            put("coverFileName", "9d8f4978-0ef8-42d0-873a-4eb583439237HP2.jpg");
        }};
        String response = mapper.writeValueAsString(List.of(book));
        try (NetworkInterceptor ignored =
                     new NetworkInterceptor(
                             Driver.get(),
                             Route.matching(req -> req.getUri().endsWith("/api/book/")).to(
                                     () ->
                                             req ->
                                                 new HttpResponse()
                                                         .setStatus(200)
                                                         .addHeader("Content-Type", MediaType.HTML_UTF_8.toString())
                                                         .setContent(Contents.utf8String(response))
                                             ))) {
            Driver.get().get("https://bookcart.azurewebsites.net/books/details/3");
        }
    }

    @AfterEach
    public void teardown() {
        Driver.quit();
    }

    @Test
    public void changedPriceTest() {
        String title = Driver.get().findElement(By.xpath("//*[text()='Title']/ancestor::tr/td[2]"))
                .getText();
        Assertions.assertEquals("Harry Potter and the Chamber of Secrets", title);
    }
}