package ru.otus.pyltsin.HW8.MyGson;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Pyltsin on 27.05.2017.
 */
public class MyGsonImpl implements MyGson {

    @Override
    public String toJson(Object obj) throws IllegalAccessException {

        if (ReflectionHelper.isWrapperOrPrimitivesType(obj.getClass())) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

            addPrimitives(jsonObjectBuilder, "out", obj);
            return String.valueOf(jsonObjectBuilder.build().get("out"));
        }

        return addObject(obj).toString();
    }

    private JsonValue addObject(Object obj) throws IllegalAccessException {



        if (obj.getClass().isArray()) {
            return addArray(obj).build();
        }

        if (Collection.class.isAssignableFrom(obj.getClass())) {
            return addCollection((Collection) obj).build();
        }

        if (Map.class.isAssignableFrom(obj.getClass())) {
            return addMap((Map) obj).build();
        }

        JsonObjectBuilder jsonObjectBuilder = parseObject(obj);

        return jsonObjectBuilder.build();
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

            if (field.get(obj) == null) {
                continue;
            }

            if (ReflectionHelper.isWrapperOrPrimitivesType(fieldType)) {
                addPrimitives(jsonObjectBuilder, field.getName(), field.get(obj));
                continue;
            }


            jsonObjectBuilder.add(fieldName, addObject(field.get(obj)));

            field.setAccessible(accessible);
        }
        return jsonObjectBuilder;
    }

    private JsonArrayBuilder addCollection(Collection list) throws IllegalAccessException {
        return addArray(list.toArray());
    }

    private JsonArrayBuilder addArray(Object array) throws IllegalAccessException {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        if (array.getClass().isArray()) {
            int length = Array.getLength(array);

            for (int i = 0; i < length; i++) {
                Object arrayElement = Array.get(array, i);

                if (arrayElement == null) {
                    continue;
                }

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
            if (!ReflectionHelper.isWrapperOrPrimitivesType(key.getClass())) {
                throw new IllegalAccessException("Map must include only primitives");
            }
            Object value = object.get(key);
            if (ReflectionHelper.isWrapperOrPrimitivesType(value.getClass())) {
                addPrimitives(jsonObjectBuilder, String.valueOf(key), value);
            } else {
                jsonObjectBuilder.add(String.valueOf(key), parseObject(value));
            }
        }
        return jsonObjectBuilder;
    }

    private void addPrimitives(JsonObjectBuilder jsonObjectBuilder, String name, Object value) throws IllegalAccessException {


        if (value == null) {
            jsonObjectBuilder.addNull(name);
            return;
        }

        Class<?> type = value.getClass();


        if (ReflectionHelper.isString(type) || ReflectionHelper.isChar(type)) {
            jsonObjectBuilder.add(name, String.valueOf(value) );
        } else if (ReflectionHelper.isInteger(type)) {
            jsonObjectBuilder.add(name, Integer.parseInt(String.valueOf(value)));
        } else if (ReflectionHelper.isLong(type)) {
            jsonObjectBuilder.add(name, (long) value);
        } else if (ReflectionHelper.isDouble(type)) {
            jsonObjectBuilder.add(name, Double.valueOf(value.toString()));
        } else if (ReflectionHelper.isBool(type)) {
            jsonObjectBuilder.add(name, (boolean) value);
        } else {
            throw new IllegalAccessException("Not found class");
        }
    }

    private void addPrimitivesToArray(JsonArrayBuilder jsonArrayBuilder, Object value) throws IllegalAccessException {
        if (value == null) {
            jsonArrayBuilder.addNull();
            return;
        }

        Class<?> type = value.getClass();

        if (ReflectionHelper.isString(type) || ReflectionHelper.isChar(type)) {
            jsonArrayBuilder.add(String.valueOf(value) );
        } else if (ReflectionHelper.isInteger(type)) {
            jsonArrayBuilder.add(Integer.parseInt(String.valueOf(value)));
        } else if (ReflectionHelper.isLong(type)) {
            jsonArrayBuilder.add( (long) value);
        } else if (ReflectionHelper.isDouble(type)) {
            jsonArrayBuilder.add(Double.valueOf(value.toString()));
        } else if (ReflectionHelper.isBool(type)) {
            jsonArrayBuilder.add((boolean) value);
        } else {
            throw new IllegalAccessException("Not found class");
        }
    }


}
