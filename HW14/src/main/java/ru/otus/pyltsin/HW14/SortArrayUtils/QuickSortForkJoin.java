package ru.otus.pyltsin.HW14.SortArrayUtils;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import static ru.otus.pyltsin.HW14.SortArrayUtils.HelpQuickSort.partitionQuickSort;
import static ru.otus.pyltsin.HW14.SortArrayUtils.HelpQuickSort.quickSortPart;

/**
 * Created by Pyltsin on 14.10.2016.
 */
public class QuickSortForkJoin extends RecursiveAction implements Sort {
    private static final int THREASHOLD = 1_000;
    private Comparable[] array;
    private int start;
    private int end;

    public QuickSortForkJoin() {

    }


    private QuickSortForkJoin(Comparable[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }


    @Override
    public void sort(Comparable[] array) {
        ForkJoinPool pool = new ForkJoinPool();
        QuickSortForkJoin helpSort = new QuickSortForkJoin(array, 0, array.length - 1);
        pool.invoke(helpSort);
    }

    @Override
    protected void compute() {

        if (start >= end) {
            return;
        }
        int middle = partitionQuickSort(array, start, end);

        if (end - start < THREASHOLD) {
            quickSortPart(array, start, middle - 1);
            quickSortPart(array, middle + 1, end);
        } else {
            QuickSortForkJoin sort1 = new QuickSortForkJoin(array, start, middle - 1);
            QuickSortForkJoin sort2 = new QuickSortForkJoin(array, middle + 1, end);
            invokeAll(sort1, sort2);
        }
    }
}
