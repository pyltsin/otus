package ru.otus.pyltsin.HW14.SortArrayUtils;

import java.util.Random;

/**
 * Created by Pyltsin on 02.04.2016.
 */
public class TestUtils {
    public static final int MAX_NUMBER = 10000;
    static long start = 0;

    public static long start() {
        return start = System.currentTimeMillis();
    }

    public static long getElapsedTime() {
        return System.currentTimeMillis() - start;
    }


    public static Integer[] getRandomArray(int i) {
        Integer[] array = new Integer[i];
        Random random = new Random();

        for (int j = 0; j < i; j++) {
            array[j] = random.nextInt(MAX_NUMBER);
        }
        return array;
    }
}
