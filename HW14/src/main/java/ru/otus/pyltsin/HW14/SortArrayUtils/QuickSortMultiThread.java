package ru.otus.pyltsin.HW14.SortArrayUtils;

/**
 * Created by Pyltsin on 14.10.2016.
 */
public class QuickSortMultiThread implements Sort {

    public void sort(Comparable[] array) {
        Thread quickThread = new HelpQuickSort(array, 0, array.length - 1);
        quickThread.start();
        try {
            quickThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
