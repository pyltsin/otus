package HW11.service.Helper;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pyltsin on 27.05.2017.
 */
@SuppressWarnings("ALL")
public class ReflectionHelper {
    private static final Set<Class<?>> WRAPPER_TYPES_STRING = getWrapperTypesString();
    private static final Set<Class<?>> WRAPPER_TYPES_BOOLEAN = getWrapperTypesBoolean();
    private static final Set<Class<?>> WRAPPER_TYPES_INT = getWrapperTypesInt();
    private static final Set<Class<?>> WRAPPER_TYPES_DOUBLE = getWrapperTypesDouble();
    private static final Set<Class<?>> WRAPPER_TYPES_LONG = getWrapperTypesLong();
    private static final Set<Class<?>> WRAPPER_TYPES_CHAR = getWrapperTypesChar();

    private static Set<Class<?>> getWrapperTypesChar() {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(Character.class);
        ret.add(char.class);
        return ret;
    }


    public static boolean isWrapperOrPrimitivesType(Class<?> clazz) {
        return WRAPPER_TYPES_STRING.contains(clazz) || WRAPPER_TYPES_BOOLEAN.contains(clazz) ||
                WRAPPER_TYPES_INT.contains(clazz) || WRAPPER_TYPES_DOUBLE.contains(clazz) ||
                WRAPPER_TYPES_LONG.contains(clazz) || WRAPPER_TYPES_CHAR.contains(clazz);
    }


    private static boolean iterForWrap(Class<?> type, Set<Class<?>> WRAPPER_TYPES) {
        for (Class<?> aClass : WRAPPER_TYPES) {
            boolean flag = aClass.isAssignableFrom(type);
            if (flag) {
                return true;
            }
        }
        return false;
    }

    public static boolean isString(Class<?> type) {
        return iterForWrap(type, WRAPPER_TYPES_STRING);
    }

    public static boolean isInteger(Class<?> type) {
        return iterForWrap(type, WRAPPER_TYPES_INT);
    }

    public static boolean isBool(Class<?> type) {
        return iterForWrap(type, WRAPPER_TYPES_BOOLEAN);
    }

    public static boolean isDouble(Class<?> type) {
        return iterForWrap(type, WRAPPER_TYPES_DOUBLE);
    }

    private static Set<Class<?>> getWrapperTypesString() {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(String.class);
        return ret;
    }

    private static Set<Class<?>> getWrapperTypesBoolean() {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(boolean.class);
        ret.add(Boolean.class);
        return ret;
    }

    private static Set<Class<?>> getWrapperTypesLong() {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(Long.class);
        ret.add(long.class);
        return ret;
    }

    private static Set<Class<?>> getWrapperTypesInt() {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(byte.class);
        ret.add(short.class);
        ret.add(int.class);
        return ret;
    }

    private static Set<Class<?>> getWrapperTypesDouble() {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(float.class);
        ret.add(double.class);
        return ret;
    }


    public static boolean isChar(Class<?> type) {
        return iterForWrap(type, WRAPPER_TYPES_CHAR);
    }

    public static boolean isLong(Class<?> type) {
        return iterForWrap(type, WRAPPER_TYPES_LONG);
    }
}
