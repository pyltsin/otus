package ru.otus.pyltsin.HW3.MyCollection;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Pyltsin on 16.04.2017.
 */
public class MyArrayListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private MyArrayList<Integer> myArrayList;

    @Before
    public void setUp() throws Exception {
        myArrayList = new MyArrayList<>();
    }

    @Test
    public void size() throws Exception {
        assertEquals(0, myArrayList.size());
        myArrayList.add(1);
        assertEquals(1, myArrayList.size());
    }

    @Test
    public void isEmpty() throws Exception {
        assertTrue(myArrayList.isEmpty());
        myArrayList.add(1);
        assertFalse(myArrayList.isEmpty());
    }

    @Test
    public void contains() throws Exception {
        myArrayList.add(1);
        myArrayList.add(2);
        myArrayList.add(3);
        assertTrue(myArrayList.contains(3));
        assertFalse(myArrayList.contains(5));

    }

    @Test
    public void containsEmpty() throws Exception {
        assertFalse(myArrayList.contains(5));
    }

    @Test
    public void toArray() throws Exception {
        myArrayList.add(1);
        myArrayList.add(2);
        myArrayList.add(3);
        assertArrayEquals(new Integer[]{1, 2, 3}, myArrayList.toArray());
    }

    @Test
    public void toArrayEmpty() throws Exception {
        assertArrayEquals(new Integer[]{}, myArrayList.toArray());
    }

    @Test
    public void addGet() throws Exception {
        int max = 1_000_000;
        for (int i = 0; i < max; i++) {
            myArrayList.add(i + 1);
        }
        for (int i = 0; i < max; i++) {
            assertEquals(new Integer(i + 1), myArrayList.get(i));
        }
    }

    @Test
    public void addGetSetRemoveException() throws Exception {
        int max = 1_000_000;
        for (int i = 0; i < max; i++) {
            myArrayList.add(i + 1);
        }

        try {
            myArrayList.set(1_100_000, 1);
            fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        try {
            myArrayList.set(-1, 1);
            fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        try {
            myArrayList.add(1_100_000, 1);
            fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        try {
            myArrayList.add(-1, 1);
            fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        try {
            myArrayList.get(1_100_000);
            fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        try {
            myArrayList.get(-1);
            fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        try {
            myArrayList.remove(1_100_000);
            fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        try {
            myArrayList.remove(-1);
            fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

    }

    @Test
    public void addRemoveGet() throws Exception {
        int max = 1_000;
        for (int i = 0; i < max; i++) {
            myArrayList.add(i + 1);
        }
        assertEquals(new Integer(1), myArrayList.remove(0));

        assertEquals(new Integer(1 + 1), myArrayList.get(0));

        assertEquals(max - 1, myArrayList.size());
    }

    @Test
    public void setGet() throws Exception {
        myArrayList.add(1);
        myArrayList.add(2);
        myArrayList.add(3);

        myArrayList.set(1, 10);
        assertEquals(Integer.valueOf(10), myArrayList.get(1));
        assertEquals(3, myArrayList.size());
    }

    @Test
    public void listIterator() throws Exception {
        int[] testArray = new int[]{1, 2, 3};
        for (int num : testArray) {
            myArrayList.add(num);
        }

        Iterator<Integer> integerIterator = myArrayList.listIterator();
        int i = 0;
        while (integerIterator.hasNext()) {
            int cur = integerIterator.next();
            assertEquals(testArray[i++], cur);
        }
    }

    @Test(timeout = 10_000)
    public void sort() throws Exception {
        int max = 10_000;
        Random random = new Random();
        for (int i = 0; i < max; i++) {
            myArrayList.add(random.nextInt());
        }
        Collections.sort(myArrayList);
        for (int i = 0; i < max - 1; i++) {
            assertTrue(myArrayList.get(i) < myArrayList.get(i + 1));
        }
    }

    @Test
    public void addAll() throws Exception {
        Integer[] testArray = new Integer[]{1, 2, 3};
        myArrayList.add(testArray[0]);

        Collections.addAll(myArrayList, testArray[1], testArray[2]);
        assertArrayEquals(testArray, myArrayList.toArray());
    }

    @Test
    public void copy() throws Exception {
        Integer[] testArray = new Integer[]{1, 2, 3};
        Collections.addAll(myArrayList, testArray);

        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(4);
        arrayList.add(5);

        Collections.copy(myArrayList, arrayList);

        assertArrayEquals(new Integer[]{4, 5, 3}, myArrayList.toArray());

    }
}