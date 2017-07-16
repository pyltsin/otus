package ru.otus.pyltsin.HW14.SortArrayUtils;

import java.util.Arrays;

/**
 * Created by Pyltsin on 17.07.2017.
 */
public class SortWithParallel implements Sort {
    @Override
    public void sort(Comparable[] array) {
        Arrays.parallelSort(array);
    }
}
