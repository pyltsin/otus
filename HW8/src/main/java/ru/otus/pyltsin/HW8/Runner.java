package ru.otus.pyltsin.HW8;

import com.google.gson.Gson;
import ru.otus.pyltsin.HW8.MyGson.MyGson;
import ru.otus.pyltsin.HW8.MyGson.MyGsonImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pyltsin on 27.05.2017.
 */
public class Runner {
    public static void main(String[] args) {
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

        System.out.println(simpleObjectParent);
        Gson gson = new Gson();
        String json = gson.toJson(simpleObjectParent);
        System.out.println(json);


        MyGson myGson = new MyGsonImpl();
        String myJson = null;
        try {
            myJson = myGson.toJson(simpleObjectParent);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(myJson);
    }
}
