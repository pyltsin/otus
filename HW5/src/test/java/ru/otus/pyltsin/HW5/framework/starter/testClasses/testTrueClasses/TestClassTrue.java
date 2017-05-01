package ru.otus.pyltsin.HW5.framework.starter.testClasses.testTrueClasses;

import ru.otus.pyltsin.HW5.framework.annotations.After;
import ru.otus.pyltsin.HW5.framework.annotations.Before;
import ru.otus.pyltsin.HW5.framework.annotations.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pyltsin on 01.05.2017.
 * Test for after, test, before
 */
@Test
public class TestClassTrue {
    private int i = 0;

    @Before
    /*
      test for before - i==0;
     */
    public void testBefore() {
        i = 0;
        i++;
    }

    @Test
    public void test1() {
        assertTrue(i > 0);
        i++;
    }

    @Test
    public void test2() {
        assertTrue(i > 0);
        i++;
    }

    @After
    public void after() {
        assertEquals(2, i);
    }

}
