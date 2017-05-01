package ru.otus.pyltsin.HW5.framework.helpers;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pyltsin on 01.05.2017.
 */
public class ReflectionHelperTest {
    @Test
    public void containsAnnotation() throws Exception {
        assertTrue(ReflectionHelper.containsAnnotation(ReflectionTestClass.class, ru.otus.pyltsin.HW5.framework.annotations.Test.class));
        assertFalse(ReflectionHelper.containsAnnotation(ReflectionTestClass.class, ru.otus.pyltsin.HW5.framework.annotations.Before.class));
    }

    @Test
    public void getInstance() throws Exception {
        Object testClass = ReflectionHelper.getInstance(ReflectionTestClass.class);
        assertTrue(testClass != null);
    }

    @Test
    public void getMethodsWithAnnotation() throws Exception {
        List<Method> list = ReflectionHelper.getMethodsWithAnnotation(ReflectionTestClass.class, ru.otus.pyltsin.HW5.framework.annotations.Test.class);
        assertTrue(list.size() == 1);
        assertTrue(list.get(0).getName().equals("test"));
    }

    @Test
    public void callMethod() throws Exception {
        List<Method> list = ReflectionHelper.getMethodsWithAnnotation(ReflectionTestClass.class, ru.otus.pyltsin.HW5.framework.annotations.Test.class);
        String out = (String) ReflectionHelper.callMethod(list.get(0), new ReflectionTestClass());
        assertTrue(out.equals("test"));
    }

}