package ru.otus.pyltsin.HW5.framework.exception;

/**
 * Created by Pyltsin on 01.05.2017.
 */
public class RunnerTestException extends Exception {
    RunnerTestException() {
        super();
    }

    RunnerTestException(String message) {
        super(message);
    }

    public RunnerTestException(Throwable cause) {
        super(cause);
    }
}
