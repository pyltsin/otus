package ru.otus.pyltsin.HW14.SortArrayUtils;

/**
 * Created by Pyltsin on 19.07.2017.
 */
public class QuickSortUtils {
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

}
