package ru.otus.pyltsin.HW5.framework.asserts;

/**
 * Created by Pyltsin on 01.05.2017.
 */
public class Asserts {
    public static void assertEquals(String testName, Object expected, Object actual) {
        if (expected.equals(actual)) {
            printOk(testName);
        } else {
            printFalse(testName, expected, actual);
        }
    }

    private static void printFalse(String testName, Object expected, Object actual) {
        System.out.println("Failed: " + testName + ", expected " + expected + ", actual " + actual);
    }

    private static void printOk(String testName) {
        System.out.println("Passed: " + testName);
    }

    public static void assertNotEquals(String testName, Object expected, Object actual) {
        if (!expected.equals(actual)) {
            printOk(testName);
        } else {
            printFalse(testName, expected, actual);
        }
    }

}
