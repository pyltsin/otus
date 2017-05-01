package ru.otus.pyltsin.HW5.framework.starter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.pyltsin.HW5.example.TestExample;
import ru.otus.pyltsin.HW5.example.TestExampleForFail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.fail;

/**
 * Created by Pyltsin on 01.05.2017.
 * Test not unit, but very simple and useful
 */
public class MyTesterTestWithOut {
    private PrintStream printSystem;
    private ByteArrayOutputStream outContent;

    @Before
    public void setUp() throws Exception {
        printSystem = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }


    @Test
    public void startTest() throws Exception {

        MyTester.startTest(TestExample.class, TestExampleForFail.class);

        MyTester.startTest("ru.otus.pyltsin.HW5.framework.starter.testWithOut");

        String out = outContent.toString();
        String[] line = out.split(System.lineSeparator());
        for (String s : line) {
            if ((s.contains("Passed") && !s.contains("Must pass")) || (!s.contains("Passed") && s.contains("Must pass"))) {
                fail("pass not pass");
            }
            if ((s.contains("Failed") && !s.contains("Must Fail")) || (!s.contains("Failed") && s.contains("Must Fail"))) {
                fail("fail not fail");
            }
            if (s.contains("Must don't print")) {
                fail("Call not called");
            }
        }
    }


    @After
    public void tearDown() throws Exception {
        System.setOut(printSystem);
    }
}
