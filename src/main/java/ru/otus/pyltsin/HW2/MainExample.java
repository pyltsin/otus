package ru.otus.pyltsin.HW2;

import java.lang.management.ManagementFactory;

/**
 * Created by tully.
 */
//VM options -Xmx512m -Xms512m
public class MainExample {
    public static void main(String... args) throws InterruptedException {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
//        Runtime runtime = Runtime.getRuntime();
//        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

//        runtimeMXBean.

        int size = 5 * 1024 * 1024;
        Object[] array = new Object[size];
        System.out.println("Array of size: " + array.length + " created");
        Thread.sleep(10 * 1000);

        int n = 0;
        System.out.println("Starting the loop");
        while (n < Integer.MAX_VALUE) {
            int i = n % size;
            array[i] = "";
            //new String(""); //no String pool
            n++;
            if (n % 1024 == 0) {
                Thread.sleep(1);
            }
            if (n % size == 0) {
                System.out.println("Created " + n + " objects");
                System.out.println("Creating new array");
                array = new Object[size];
                // Get current size of heap in bytes
                long heapSize = Runtime.getRuntime().totalMemory();
                System.out.println(heapSize);
// Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
                long heapMaxSize = Runtime.getRuntime().maxMemory();
                System.out.println(heapMaxSize);

                // Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
                long heapFreeSize = Runtime.getRuntime().freeMemory();
                System.out.println(heapFreeSize);

            }
        }
    }
}