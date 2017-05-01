package ru.otus.pyltsin.HW5.framework.starter.testWithOut;

import ru.otus.pyltsin.HW5.framework.annotations.After;
import ru.otus.pyltsin.HW5.framework.annotations.Before;
import ru.otus.pyltsin.HW5.framework.annotations.Test;
import ru.otus.pyltsin.HW5.framework.asserts.Asserts;

/**
 * Created by Pyltsin on 01.05.2017.
 */
@Test
public class TestExample {
    private int i = 0;

    @Before
    public void beforeI() {
        i = 0;
        i++;
    }

    @Test
    public void testI() {
        Asserts.assertEquals("TestExample.testI Must pass", 1, i);
        i++;
    }

    @Test
    public void testII() {
        Asserts.assertEquals("TestExample.testI Must pass", 1, i);
        i++;
    }

    @Test
    public void testNotEquals() {
        Asserts.assertNotEquals("TestExample.testNotEquals Must pass", 1, 2);
        i++;
    }

    @Test
    public void testNotNull() {
        Asserts.assertNotNull("TestExample.testNotNull Must pass", 1);
        i++;
    }


    @After
    public void after() {
        Asserts.assertEquals("TestExample.after Must pass", 2, i);
    }

    public void emptyTest() {
        System.out.println("TestExample.after. Must don'r print");
    }
}
