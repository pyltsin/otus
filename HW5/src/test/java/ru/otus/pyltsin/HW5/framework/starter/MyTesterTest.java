package ru.otus.pyltsin.HW5.framework.starter;

import org.junit.Test;
import ru.otus.pyltsin.HW5.framework.starter.testClasses.testFalseClasses.TestClassFalse;
import ru.otus.pyltsin.HW5.framework.starter.testClasses.testFalseClasses.TestWithoutAnnTest;
import ru.otus.pyltsin.HW5.framework.starter.testClasses.testTrueClasses.TestClassSimple;
import ru.otus.pyltsin.HW5.framework.starter.testClasses.testTrueClasses.TestClassTrue;

/**
 * Created by Pyltsin on 01.05.2017.
 * Test for take Exception
 */
public class MyTesterTest {
    @Test
    public void startTest() throws Exception {
        MyTester.startTest(TestClassTrue.class, TestClassSimple.class);
    }

    @Test(expected = java.lang.AssertionError.class)
    public void startTestCatchFail() throws Exception {
        MyTester.startTest(TestClassFalse.class);
    }

    @Test(expected = ru.otus.pyltsin.HW5.framework.exception.IllegalAnnotationTestException.class)
    public void startTestCatchNotAnn() throws Exception {
        MyTester.startTest(TestWithoutAnnTest.class);
    }


    @Test
    public void startTestForPackage() throws Exception {
        MyTester.startTest("ru.otus.pyltsin.HW5.framework.starter.testClasses.testTrueClasses");
    }

    @Test(expected = java.lang.AssertionError.class)
    public void startTestForFalsePackage() throws Exception {
        MyTester.startTest("ru.otus.pyltsin.HW5.framework.starter.testClasses.testFalseClasses");
    }
}