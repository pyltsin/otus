package ru.otus.pyltsin.HW8.MyGson;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import ru.otus.pyltsin.HW8.SimpleObject;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pyltsin on 28.05.2017. Algo8
 */
public class MyGsonImplTest {
    Gson gson;
    MyGson myGson;

    @Before
    public void setUp() throws Exception {
        gson = new Gson();
        myGson = new MyGsonImpl();
    }


    @Test
    public void toJsonCollection() throws Exception {
        Collection<Integer> collection = new ArrayList<>();
        collection.add(5);
        collection.add(6);

        assertEquals(gson.toJson(collection), myGson.toJson(collection));
    }


    @Test
    public void toJsonPrim() throws Exception {
        List<Object> list = new ArrayList<>();
        list.add(1);
        list.add(1L);
        list.add("1");
        list.add('1');
        list.add(1.0);
        list.add(true);
        list.add(Byte.parseByte("4"));
        list.add(Short.parseShort("5"));
        list.add(Float.parseFloat("5."));

        for (Object o : list) {
            assertEquals(gson.toJson(o), myGson.toJson(o));
        }
    }

    @Test
    public void toJsonArray() throws Exception {
        int[] ar = new int[]{1, 2, 3, 4, 5};
        assertEquals(gson.toJson(ar), myGson.toJson(ar));
    }

    @Test
    public void toJsonMap() throws Exception {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 1);
        assertEquals(gson.toJson(map), myGson.toJson(map));
    }

    @Test
    public void toJsonFullObject() throws Exception {
        SimpleObject simpleObject = new SimpleObject();
        simpleObject.setI(5);
        simpleObject.setString("test");
        simpleObject.setBool(true);
        simpleObject.setDoubles(new double[]{1.0, 2.5});
        simpleObject.setADouble(1.6);
        List<Integer> l = new ArrayList<>();
        l.add(5);
        l.add(8);
        simpleObject.setList(l);
        Map<String, Double> map = new HashMap<>();
        map.put("one", 1.0);
        map.put("two", 2.0);
        simpleObject.setMap(map);
        simpleObject.setNonPrint("not pint");

        assertEquals(gson.toJson(simpleObject), myGson.toJson(simpleObject));

    }

    @Test
    public void toJsonFullObjectWithInner() throws Exception {
        SimpleObject simpleObjectParent = new SimpleObject();
        SimpleObject simpleObject = new SimpleObject();
        simpleObjectParent.setI(5);
        simpleObject.setString("test");
        simpleObject.setBool(true);
        simpleObject.setDoubles(new double[]{1.0, 2.5});
        simpleObject.setADouble(1.6);
        List<Integer> l = new ArrayList<>();
        l.add(5);
        l.add(8);
        simpleObject.setList(l);
        Map<String, Double> map = new HashMap<>();
        map.put("one", 1.0);
        map.put("two", 2.0);
        simpleObject.setMap(map);
        simpleObject.setNonPrint("not pint");
        simpleObjectParent.setSimpleObject(simpleObject);

        assertEquals(gson.toJson(simpleObjectParent), myGson.toJson(simpleObjectParent));

    }

}