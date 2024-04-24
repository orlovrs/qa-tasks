package testing.tasks;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class Snake {
    private List<Integer> snake(int[][] array) {
        List<Integer> result = new ArrayList<>();
        if (array.length == 1 && array[0].length == 0) {
            return result;
        }

        int[] xBoundary = new int[] {0, array.length - 1};
        int[] yBoundary = new int[] {0, array.length - 1};
        int counter = 0;

        while (counter < array.length * array.length) {
            for (int i = xBoundary[0]; i <= xBoundary[1]; i++) {
                result.add(array[yBoundary[0]][i]);
                counter++;
            }
            yBoundary[0]++;

            for (int i = yBoundary[0]; i <= yBoundary[1]; i++) {
                result.add(array[i][xBoundary[1]]);
                counter++;
            }
            xBoundary[1]--;

            for (int i = xBoundary[1]; i >= xBoundary[0]; i--) {
                result.add(array[yBoundary[1]][i]);
                counter++;
            }
            yBoundary[1]--;

            for (int i = yBoundary[1]; i >= yBoundary[0]; i--) {
                result.add(array[i][xBoundary[0]]);
                counter++;
            }
            xBoundary[0]++;
        }

        return result;
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void test(int[][] array, int[] result) {
        assertArrayEquals(result, snake(array).stream()
                .mapToInt(i -> i)
                .toArray());
    }

    public static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of(new int[][]{{}}, new int[]{}),
                Arguments.of(new int[][]{{1}}, new int[]{1}),
                Arguments.of(new int[][]{
                        {1, 2},
                        {4, 3}
                }, new int[]{1, 2, 3, 4}),
                Arguments.of(new int[][]{
                        {1, 2, 3},
                        {8, 9, 4},
                        {7, 6, 5}
                }, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}),
                Arguments.of(new int[][]{
                        {1, 2, 3, 4},
                        {12, 13, 14, 5},
                        {11, 16, 15, 6},
                        {10, 9, 8, 7}
                }, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}),
                Arguments.of(new int[][]{
                        {1,  2,  3,  4,  5},
                        {16, 17, 18, 19, 6},
                        {15, 24, 25, 20, 7},
                        {14, 23, 22, 21, 8},
                        {13, 12, 11, 10, 9}
                }, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25})
        );
    }
}
