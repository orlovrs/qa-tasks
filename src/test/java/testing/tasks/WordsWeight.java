package testing.tasks;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Дан список из строк, состоящих из букв a, b, c (aaa, acb, ababababa...)
Каждая буква имеет цену (a = 3, b = 2, c = 1)
Найти в массиве слово с максимальной ценой. Если таких слов несколько,
то выбрать слово с наименьшим количеством букв. Если слов с одинаковым количеством букв
несколько, то вывести все

Найти результат для набора строк: aaaa, bbbb, cccc, abab, abcabc, aaccaab, aabaab
 */
public class WordsWeight {
    private Map<Character, Integer> map = new HashMap<>() {{
        put('a', 3);
        put('b', 2);
        put('c', 1);
    }};

    private int getWordWeight(String word) {
        int price = 0;
        for (Character c : word.toCharArray()) {
            price += map.get(c);
        }

        return price;
    }

    private List<String> findMax(String[] words) {
        int maxWeight = Integer.MIN_VALUE;
        Set<String> result = new HashSet<>();

        for (String word : words) {
            int weight = getWordWeight(word);
            if (weight > maxWeight) {
                maxWeight = weight;
                result.clear();
                result.add(word);
            } else if (weight == maxWeight) {
                if (result.stream().anyMatch(w -> word.length() < w.length())) {
                    result.clear();
                }
                result.add(word);
            }
        }

        return new ArrayList<>(result);
    }

    @Test
    public void testTask() {
        assertEquals(new ArrayList<String>() {{
            add("aabaab");
        }}, findMax(new String[]{
                "aaaa", // 12
                "bbbb", // 8
                "cccc", // 4
                "abab", // 10
                "abcabc", // 12
                "aaccaab", // 16
                "aabaab" // 16
        }));
    }
}