package ru.otus.pyltsin.HW5.framework.starter;

import org.reflections.Reflections;
import ru.otus.pyltsin.HW5.framework.annotations.After;
import ru.otus.pyltsin.HW5.framework.annotations.Before;
import ru.otus.pyltsin.HW5.framework.annotations.Test;
import ru.otus.pyltsin.HW5.framework.exception.IllegalAnnotationTestException;
import ru.otus.pyltsin.HW5.framework.exception.RunnerTestException;
import ru.otus.pyltsin.HW5.framework.helpers.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Pyltsin on 01.05.2017.
 */
public class MyTester {


    public static void startTest(String path) throws RunnerTestException {
        helpStartTest(getClasses(path));
    }

    /**
     * По словам преподавателя - пункт не обязательный, поэтому для реализации воспользуемся сторонней библиотекой.
     */
    private static List<Class<?>> getClasses(String path) {
        Reflections reflections = new Reflections(path);
        return new ArrayList<>(reflections.getTypesAnnotatedWith(Test.class));
    }

    public static void startTest(Class<?>... classes) throws RunnerTestException {
        helpStartTest(Arrays.asList(classes));
    }

    private static void helpStartTest(List<Class<?>> classes) throws RunnerTestException {
        if (classes == null || classes.size() == 0) {
            throw new IllegalArgumentException("Classes not found");
        }
        for (Class<?> aClass : classes) {
            if (!ReflectionHelper.containsAnnotation(aClass, Test.class)) {
                throw new IllegalAnnotationTestException("Class not contain Annotation Test");
            }
            startTestClass(aClass);
        }

    }

    private static <T> void startTestClass(Class<T> aClass) throws RunnerTestException {
        if (aClass == null) {
            throw new IllegalArgumentException("Classes not found");
        }
        Method methodsBefore;
        List<Method> methodsTest;
        Method methodsAfter;
        try {
            List<Method> before = getMethodsBefore(aClass);
            List<Method> after = getMethodsAfter(aClass);
            if (before.size() > 1 || after.size() > 1) {
                throw new IllegalAnnotationTestException("Many @before or @after");
            }
            methodsBefore = before.size() == 1 ? before.get(0) : null;
            methodsTest = getMethodsTest(aClass);
            methodsAfter = after.size() == 1 ? after.get(0) : null;

            if ((methodsBefore != null && methodsTest.contains(methodsBefore)) ||
                    (methodsAfter != null && methodsTest.contains(methodsAfter))
                    ) {
                throw new IllegalAnnotationTestException("@After or @Before contain @Test");
            }

        } catch (IllegalAnnotationTestException e) {
            throw new RunnerTestException(e);
        }

        T ins = ReflectionHelper.getInstance(aClass);
        runMethods(methodsBefore, methodsTest, methodsAfter, ins);
    }

    private static void runMethods(Method methodsBefore, List<Method> methodsTest, Method methodsAfter, Object aClass) throws RunnerTestException {

        for (Method method : methodsTest) {
            if (methodsBefore != null) {
                runTest(methodsBefore, aClass);
            }
            runTest(method, aClass);
            if (methodsAfter != null) {
                runTest(methodsAfter, aClass);
            }
        }
    }

    private static void runTest(Method method, Object object) throws RunnerTestException {

        try {
            ReflectionHelper.callMethod(method, object);
        } catch (InvocationTargetException e) {
            Throwable throwable = e.getTargetException();
            if (throwable instanceof AssertionError) {
                throw (AssertionError) throwable;
            } else {
                throw new RunnerTestException(e);
            }
        } catch (IllegalAccessException e) {
            System.out.println("ERROR. Not access to method");
            throw new RunnerTestException(e);
        }

    }

    private static <T> List<Method> getMethodsAfter(Class<T> ins) {
        return ReflectionHelper.getMethodsWithAnnotation(ins, After.class);
    }

    private static <T> List<Method> getMethodsTest(Class<T> ins) {
        return ReflectionHelper.getMethodsWithAnnotation(ins, Test.class);
    }

    private static <T> List<Method> getMethodsBefore(Class<T> ins) {
        return ReflectionHelper.getMethodsWithAnnotation(ins, Before.class);
    }


}
