package testing.tasks;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
#1. Написать функцию, которая инкрементирует значение эндпоинта на N-ое кол-во
и возвращает полученный результат. Учесть, что ссылка может содержать параметры запроса
https://lubart-miniatures.com/shop/page/2/

#2 Написать функцию, которая принимает на вход массив ссылок подобного вида, проверяет
страницу на существование и возвращает список всех несуществующих страниц

#3 Написать тест, проверяющий корректность работы этой функции. Входные данные должны
обрабатываться одним методом
 */
public class CheckSimilarLinks {
    private String getIncrementedLink(String url, int n) {
        String[] parts = url.split("/", -1);
        String newPageNumber = String.valueOf(Integer.parseInt(parts[5]) + n);
        parts[5] = newPageNumber;
        return String.join("/", parts);
    }

    private List<String> getNonexistentLinks(String[] urls) {
        List<String> nonExistentLinks = new ArrayList<>();

        for (String url : urls) {
            String title = given()
                    .when().get(url)
                    .then().extract().body().htmlPath().get("html.head.title");
            if (title.contains("Page not found"))
                nonExistentLinks.add(url);
        }

        return nonExistentLinks;
    }

    @ParameterizedTest
    @MethodSource("arrayLinks")
    public void testNonExistentLinks(String[] urls, String[] expectedLinks) {
        List<String> actualLinks = getNonexistentLinks(urls);
        assertEquals(expectedLinks.length, actualLinks.size());

        for (String link : expectedLinks) {
            assertTrue(actualLinks.contains(link));
        }
    }

    private static Stream<Arguments> arrayLinks() {
        return Stream.of(
                Arguments.of(
                        new String[]{}, new String[]{}
                ),
                Arguments.of(
                        new String[]{"https://lubart-miniatures.com/shop/page/2/"},
                        new String[]{}
                ),
                Arguments.of(
                        new String[]{"https://lubart-miniatures.com/shop/page/10000/"},
                        new String[]{"https://lubart-miniatures.com/shop/page/10000/"}
                ),
                Arguments.of(
                        new String[]{
                                "https://lubart-miniatures.com/shop/page/2/",
                                "https://lubart-miniatures.com/shop/page/3/"
                        },
                        new String[]{}
                ),
                Arguments.of(
                        new String[]{
                                "https://lubart-miniatures.com/shop/page/10000/",
                                "https://lubart-miniatures.com/shop/page/10001/"
                        },
                        new String[]{
                                "https://lubart-miniatures.com/shop/page/10000/",
                                "https://lubart-miniatures.com/shop/page/10001/"
                        }
                ),
                Arguments.of(
                        new String[]{
                                "https://lubart-miniatures.com/shop/page/2/",
                                "https://lubart-miniatures.com/shop/page/10001/"
                        },
                        new String[]{
                                "https://lubart-miniatures.com/shop/page/10001/"
                        }
                )
        );
    }
}
