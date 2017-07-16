package ru.otus.pyltsin.HW14.SortArrayUtils;

/**
 * Created by Pyltsin on 02.10.2016.
 */
public class HelpQuickSort extends Thread {
    //    private static int iter = 0;
    private static int maxThread;

    private volatile Comparable[] array;
    private int startI;
    private int endI;

    HelpQuickSort(Comparable[] array, int startI, int endI) {
        this.array = array;
        this.startI = startI;
        this.endI = endI;
        maxThread = Runtime.getRuntime().availableProcessors();
    }


    /**
     * Helped method. Sort only part in [start, end]
     *
     * @param array
     * @param start
     * @param end
     */
    public static void quickSortPart(Comparable[] array, int start, int end) {
        if (start >= end) {
            return;
        }
        int middle = partitionQuickSort(array, start, end);
        quickSortPart(array, start, middle - 1);
        quickSortPart(array, middle + 1, end);
    }

    /**
     * Replace in [start, end] so that: array[start, middle)<array[middle]<array(middle, end]
     *
     * @param array
     * @param start
     * @param end
     * @return index for middle
     */
    @SuppressWarnings("Duplicates")
    public static int partitionQuickSort(Comparable[] array, int start, int end) {
        int i = start + 1;
        int j = end;
        while (true) {
            while (i <= end && isLessOrEquals(array[i], array[start])) {
                i++;
            }
            while (j >= start + 1 && isLessOrEquals(array[start], array[j])) {
                j--;
            }
            if (i >= j) {
                break;
            }
            swap(array, i, j);
        }
        swap(array, start, j);
        return j;
    }

    /**
     * Return middle index.
     *
     * @param start
     * @param end
     * @return middle index
     */
    private static int middle(int start, int end) {
        return (end - start) / 2 + start;
    }

    private static boolean isLessOrEquals(Comparable a, Comparable b) {
        return a.compareTo(b) < 0 || a.equals(b);
    }

    private static void swap(Comparable[] array, int i, int j) {
        Comparable temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public void run() {
        if (startI >= endI) {
            return;
        }
        int middle = partitionQuickSort(array, startI, endI);
        if (maxThread > 2) {
            maxThread -= 2;
            Thread quickThread1 = new HelpQuickSort(array, startI, middle - 1);
            Thread quickThread2 = new HelpQuickSort(array, middle + 1, endI);

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
