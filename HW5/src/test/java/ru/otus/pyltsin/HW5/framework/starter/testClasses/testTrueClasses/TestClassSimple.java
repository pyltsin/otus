package ru.otus.pyltsin.HW5.framework.starter.testClasses.testTrueClasses;

import ru.otus.pyltsin.HW5.framework.annotations.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pyltsin on 01.05.2017.
 * Simple test. Only test for all work
 */
@Test
public class TestClassSimple {
    @Test
    public void test(){
        assertEquals(1, 1);
    }
}
