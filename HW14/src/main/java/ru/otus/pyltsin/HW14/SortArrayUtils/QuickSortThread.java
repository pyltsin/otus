package ru.otus.pyltsin.HW14.SortArrayUtils;

import static ru.otus.pyltsin.HW14.SortArrayUtils.QuickSortUtils.partitionQuickSort;
import static ru.otus.pyltsin.HW14.SortArrayUtils.QuickSortUtils.quickSortPart;

/**
 * Created by Pyltsin on 02.10.2016.
 */
public class QuickSortThread extends Thread {
    private static int maxThread;

    private Comparable[] array;
    private int startI;
    private int endI;

    QuickSortThread(Comparable[] array, int startI, int endI) {
        this.array = array;
        this.startI = startI;
        this.endI = endI;
        maxThread = Runtime.getRuntime().availableProcessors();
    }

    public void run() {
        if (startI >= endI) {
            return;
        }
        int middle = partitionQuickSort(array, startI, endI);
        if (maxThread > 2) {
            maxThread -= 2;
            Thread quickThread1 = new QuickSortThread(array, startI, middle - 1);
            Thread quickThread2 = new QuickSortThread(array, middle + 1, endI);

            quickThread1.start();
            quickThread2.start();
            try {
                quickThread1.join();
                quickThread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            quickSortPart(array, startI, middle - 1);
            quickSortPart(array, middle + 1, endI);
        }
    }
}
