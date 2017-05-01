package ru.otus.pyltsin.HW5.framework.starter.testWithOut;

import ru.otus.pyltsin.HW5.framework.annotations.Test;
import ru.otus.pyltsin.HW5.framework.asserts.Asserts;

/**
 * Created by Pyltsin on 01.05.2017.
 */
@Test
class TestExampleForFail {
    @Test
    public void test() {
        Asserts.assertEquals("TestExampleForFail.test Must Fail", 1, 2);
    }
}
