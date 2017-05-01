package ru.otus.pyltsin.HW3;

import ru.otus.pyltsin.HW3.MyCollection.MyArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Pyltsin on 16.04.2017. Algo8
 */
public class Runner {
    private static final int MEASURE_COUNT = 100;

    public static void main(String[] args) {

        List<Integer> myList = new MyArrayList<>();
        fillList(myList, 5);

        System.out.println("Simple Test:");
        printList(myList, "MyList Start");

        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(14);
        arrayList.add(15);
        arrayList.add(16);

        Collections.sort(myList);
        printList(myList, "MyList Sort");

        Collections.addAll(myList, 11, 12, 13);

        printList(myList, "MyList Add");

        Collections.copy(myList, arrayList);
        printList(myList, "MyList with copy");

        System.out.println();

        System.out.println("Test time:");
        System.out.println("Test time 'add': ");

        int max = 1_000_000;
        calcTime(() -> fillList(new MyArrayList<>(), max), "myArrayList");
        calcTime(() -> fillList(new ArrayList<>(), max), "ArrayList");

        System.gc();

        System.out.println("Test time 'remove': ");
        myList = new MyArrayList<>();
        fillList(myList, max);
        arrayList = new ArrayList<>();
        fillList(arrayList, max);

        System.gc();

        List<Integer> finalMyList = myList;
        calcTime(() -> finalMyList.remove(0), "myArrayList");
        List<Integer> finalArrayList = arrayList;
        calcTime(() -> finalArrayList.remove(0), "ArrayList");


        System.out.println("Test time 'contains': ");

        myList = new MyArrayList<>();
        fillListByOrder(myList, max);
        arrayList = new ArrayList<>();
        fillListByOrder(arrayList, max);

        System.gc();
        List<Integer> finalMyListCon = myList;
        calcTime(() -> finalMyListCon.contains(max), "myArrayList");
        List<Integer> finalArrayListCon = arrayList;
        calcTime(() -> finalArrayListCon.contains(max), "ArrayList");
    }

    private static void fillListByOrder(List<Integer> myList, @SuppressWarnings("SameParameterValue") int size) {
        for (int j = 0; j < size; j++) {
            myList.add(j);
        }
    }

    private static void printList(List<Integer> myList, String txt) {
        System.out.println(txt + ":");
        for (Integer integer : myList) {
            System.out.print(integer + " ");
        }
        System.out.println();
    }

    private static void fillList(List<Integer> myList, int size) {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            myList.add(random.nextInt(10));
        }
    }

    private static void calcTime(Runnable runnable, String txt) {
        long startTime = System.nanoTime();
        for (int i = 0; i < MEASURE_COUNT; i++)
            runnable.run();
        long finishTime = System.nanoTime();
        long timeNs = (finishTime - startTime) / MEASURE_COUNT;
        System.out.println(txt);
        System.out.println("Time spent: " + timeNs + "ns (" + timeNs / 1_000_000 + "ms)");
    }

}
