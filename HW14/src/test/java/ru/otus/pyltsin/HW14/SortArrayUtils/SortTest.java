package ru.otus.pyltsin.HW14.SortArrayUtils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Pyltsin on 17.07.2017.
 */
public class SortTest {
    private static final int MAX_TEST = 10;
    private static final int MAX_NUMBER = 1000;

    @Test
    public void SortStreamTest() throws Exception {
        testSort(new SortWithStream(), "SortWithStream");
    }

    @Test
    public void QuickSortForkJoinTest() throws Exception {
        testSort(new QuickSortForkJoin(), "QuickSortForkJoin");
    }

    @Test
    public void QuickSortMultiThreadTest() throws Exception {
        testSort(new QuickSortMultiThread(), "QuickSortMultiThread");
    }

    @Test
    public void SortWithParallelTest() throws Exception {
        testSort(new SortWithParallel(), "SortWithParallel");
    }

    private void testSort(Sort sort, String nameSort) {
        System.out.println(nameSort);
        for (int i = 0; i < MAX_TEST; i++) {
            Integer[] array = TestUtils.getRandomArray(MAX_NUMBER);
            sort.sort(array);
            checkArray(array);
        }
    }

    private void checkArray(Integer[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            Integer integer = array[i];
            Integer integer2 = array[i + 1];
            assertTrue(integer <= integer2);
        }
    }


}