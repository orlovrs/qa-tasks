package testing.tasks;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/*
Найти уникальные слова в строке, метод должен возвращать массив с уникальными словами
 */
public class UniqueWords {
    public static Stream<Arguments> strings() {
        return Stream.of(
                Arguments.of("", List.of()),
                Arguments.of("test", List.of("test")),
                Arguments.of("test test2", List.of("test", "test2")),
                Arguments.of("test test2 test", List.of("test2")),
                Arguments.of("test test2 test2 test", List.of()),
                Arguments.of("test test2 test2 test test", List.of())
        );
    }

    @ParameterizedTest
    @MethodSource("strings")
    public void testTask(String string, List<String> expected) {
        List<String> actual = findUnique(string);
        assertEquals(expected.size(), actual.size());
        for (String word : actual) {
            assertTrue(expected.contains(word));
        }
    }

    private List<String> findUnique(String str) {
        if (str.isBlank())
            return new ArrayList<>();

        List<String> result = new ArrayList<>();
        String[] words = str.split(" ");
        for (String word : words) {
            if (Arrays
                    .stream(words)
                    .filter(w -> w.equals(word))
                    .count() == 1) {
                result.add(word);
            }
        }

        return result;
    }
}
