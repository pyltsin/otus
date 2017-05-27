package ru.otus.pyltsin.HW8.MyGson;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

/**
 * Created by Pyltsin on 27.05.2017.
 */
public class MyGsonImpl implements MyGson {

    @Override
    public String toJson(Object obj) throws IllegalAccessException {
        JsonObjectBuilder jsonObjectBuilder = parseObject(obj);
        return jsonObjectBuilder.build().toString();
    }

    private JsonObjectBuilder parseObject(Object obj) throws IllegalAccessException {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (Field field : obj.getClass().getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            String fieldName = field.getName();
            Class fieldType = field.getType();
            boolean isTransient = Modifier.isTransient(field.getModifiers());

            if (isTransient) {
                continue;
            }

            if (ReflectionHelper.isWrapperOrPrimitivesType(fieldType)) {
                addPrimitives(jsonObjectBuilder, field.getName(), field.get(obj));
                continue;
            }

            if (fieldType.isArray()) {
                jsonObjectBuilder.add(fieldName, addArray(field.get(obj)));
                continue;
            }

            if (List.class.isAssignableFrom(fieldType)) {
                jsonObjectBuilder.add(fieldName, addList((List) field.get(obj)));
                continue;
            }

            if (Map.class.isAssignableFrom(fieldType)) {
                jsonObjectBuilder.add(fieldName, addMap((Map) field.get(obj)));
                continue;
            }

            jsonObjectBuilder.add(fieldName, parseObject(field.get(obj)));

            field.setAccessible(accessible);
        }
        return jsonObjectBuilder;
    }

    private JsonArrayBuilder addList(List list) throws IllegalAccessException {
        return addArray(list.toArray());
    }

    private JsonArrayBuilder addArray(Object array) throws IllegalAccessException {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        if (array.getClass().isArray()) {
            int length = Array.getLength(array);

            for (int i = 0; i < length; i++) {
                Object arrayElement = Array.get(array, i);
                if (ReflectionHelper.isWrapperOrPrimitivesType(arrayElement.getClass())) {
                    addPrimitivesToArray(jsonArrayBuilder, arrayElement);
                } else {
                    jsonArrayBuilder.add(parseObject(arrayElement));
                }
            }
        }
        return jsonArrayBuilder;
    }


    private JsonObjectBuilder addMap(Map object) throws IllegalAccessException {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (Object key : object.keySet()) {
            if (!ReflectionHelper.isString(key.getClass())) {
                throw new IllegalAccessException("Map must include only string type key");
            }
            Object value = object.get(key);
            if (ReflectionHelper.isWrapperOrPrimitivesType(value.getClass())) {
                addPrimitives(jsonObjectBuilder, (String) key, value);
            } else {
                jsonObjectBuilder.add((String) key, parseObject(value));
            }
        }
        return jsonObjectBuilder;
    }

    private void addPrimitives(JsonObjectBuilder jsonObjectBuilder, String name, Object value) throws IllegalAccessException {
        Class<?> type = value.getClass();

        if (ReflectionHelper.isString(type)) {
            jsonObjectBuilder.add(name, (String) value);
        } else if (ReflectionHelper.isInteger(type)) {
            jsonObjectBuilder.add(name, (int) value);
        } else if (ReflectionHelper.isDouble(type)) {
            jsonObjectBuilder.add(name, (double) value);
        } else if (ReflectionHelper.isBool(type)) {
            jsonObjectBuilder.add(name, (boolean) value);
        } else {
            throw new IllegalAccessException("Not found class");
        }
    }

    private void addPrimitivesToArray(JsonArrayBuilder jsonArrayBuilder, Object value) throws IllegalAccessException {
        Class<?> type = value.getClass();

        if (ReflectionHelper.isString(type)) {
            jsonArrayBuilder.add((String) value);
        } else if (ReflectionHelper.isInteger(type)) {
            jsonArrayBuilder.add((int) value);
        } else if (ReflectionHelper.isDouble(type)) {
            jsonArrayBuilder.add((double) value);
        } else if (ReflectionHelper.isBool(type)) {
            jsonArrayBuilder.add((boolean) value);
        } else {
            throw new IllegalAccessException("Not found class");
        }
    }


}
