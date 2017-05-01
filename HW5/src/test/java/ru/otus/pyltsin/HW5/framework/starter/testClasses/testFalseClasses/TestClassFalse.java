package ru.otus.pyltsin.HW5.framework.starter.testClasses.testFalseClasses;

import ru.otus.pyltsin.HW5.framework.annotations.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Pyltsin on 01.05.2017.
 * Test for test catch error
 */
@Test
public class TestClassFalse {
    @SuppressWarnings("ConstantConditions")
    @Test
    public void test1() {
        assertTrue(-1 > 0);
    }
}
