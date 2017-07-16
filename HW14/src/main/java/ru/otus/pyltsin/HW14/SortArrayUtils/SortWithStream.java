package ru.otus.pyltsin.HW14.SortArrayUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Pyltsin on 17.07.2017.
 */
public class SortWithStream implements Sort {
    @Override
    public void sort(Comparable[] array) {
        Arrays.asList(array).parallelStream().sorted().collect(Collectors.toList()).toArray(array);
    }
}
