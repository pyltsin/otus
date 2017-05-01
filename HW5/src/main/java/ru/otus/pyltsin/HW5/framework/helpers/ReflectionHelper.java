package ru.otus.pyltsin.HW5.framework.helpers;

import ru.otus.pyltsin.HW5.framework.exception.RunnerTestException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pyltsin on 01.05.2017.
 */
public class ReflectionHelper {
    public static <T> T getInstance(Class<T> type) throws RunnerTestException {
        try {
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RunnerTestException(e);
        }
    }

    /**
     * Return public methods with marker
     *
     */
    public static List<Method> getMethodsWithAnnotation(Class clazz, Class marker) {
        if (clazz == null || marker == null || !marker.isAnnotation()) {
            throw new IllegalArgumentException();
        }

        Method[] methods = clazz.getMethods();
        List<Method> listOut = new ArrayList<>();
        for (Method method : methods) {
            //noinspection unchecked
            if (method.getAnnotation(marker) != null ) {
                listOut.add(method);
            }
        }
        return listOut;
    }

    public static Object callMethod(Method method, Object object) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(object);
    }

    public static boolean containsAnnotation(Class clazz, Class marker) {
        if (clazz == null || marker == null || !marker.isAnnotation()) {
            throw new IllegalArgumentException();
        }

        return clazz.getAnnotation(marker) != null;
    }
}
