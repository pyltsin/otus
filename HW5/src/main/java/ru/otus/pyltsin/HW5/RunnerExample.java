package ru.otus.pyltsin.HW5;

import ru.otus.pyltsin.HW5.example.TestExample;
import ru.otus.pyltsin.HW5.example.TestExampleForFail;
import ru.otus.pyltsin.HW5.framework.exception.RunnerTestException;
import ru.otus.pyltsin.HW5.framework.starter.MyTester;

/**
 * Created by Pyltsin on 01.05.2017.
 */
public class RunnerExample {
    public static void main(String[] args) {

        try {
            MyTester.startTest(TestExample.class, TestExampleForFail.class);
        } catch (RunnerTestException e) {
            e.printStackTrace();
        }

        try {
            MyTester.startTest("ru.otus.pyltsin.HW5.example");
        } catch (RunnerTestException e) {
            e.printStackTrace();
        }

    }
}
