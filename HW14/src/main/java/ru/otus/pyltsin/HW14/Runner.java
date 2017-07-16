package ru.otus.pyltsin.HW14;

import ru.otus.pyltsin.HW14.SortArrayUtils.*;

import java.util.Arrays;

/**
 * Fixed - > add runner
 */
public class Runner {

    public static void main(String[] args) {
        System.out.println("Core = " + Runtime.getRuntime().availableProcessors());
        int lenAr = 2000000;
        Integer[] arrayForQSortMultiThread = TestUtils.getRandomArray(lenAr);
        Integer[] arrayForQSortFJoin = Arrays.copyOf(arrayForQSortMultiThread, arrayForQSortMultiThread.length);
        Integer[] arrayForParallelSort = Arrays.copyOf(arrayForQSortMultiThread, arrayForQSortMultiThread.length);
        Integer[] arrayForStream = Arrays.copyOf(arrayForQSortMultiThread, arrayForQSortMultiThread.length);
        timeForSort(new QuickSortMultiThread(), arrayForQSortMultiThread, "Start for multi thread");
        timeForSort(new QuickSortForkJoin(), arrayForQSortFJoin, "Start for forkjoin");
        timeForSort(new SortWithParallel(), arrayForParallelSort, "Start for parallelsort");
        timeForSort(new SortWithStream(), arrayForStream, "Start for stream");
    }

    public static void timeForSort(Sort sort, Integer[] array, String s) {
        System.out.println(s);
        TestUtils.start();
        sort.sort(array);
        System.out.println(TestUtils.getElapsedTime());
    }


}
