package ru.otus.pyltsin.HW2;

import ru.otus.pyltsin.HW2.StandSize.FactoryStandSize;
import ru.otus.pyltsin.HW2.StandSize.Interfaces.StandSize;

import java.lang.management.ManagementFactory;

/**
 * Created by Pyltsin on 09.04.2017. Algo8
 */

public class Runner {
    public static void main(String[] args) {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        System.out.println("Max size heap: " + Runtime.getRuntime().maxMemory());
        System.out.println("Size heap: " + Runtime.getRuntime().totalMemory());
        System.out.println("Free size heap: " + Runtime.getRuntime().freeMemory());


        System.out.println("Size object (8 bytes)");
        StandSize standSize = new FactoryStandSize(() -> new Object());
        long sizeObject = standSize.getSizeObject();
        System.out.println("Final size object: " + sizeObject);


        System.out.println("Size Empty String (40 bytes)");
        standSize = new FactoryStandSize(() -> new String());
        sizeObject = standSize.getSizeObject();
        System.out.println("Final size object: " + sizeObject);


        System.out.println("Size Empty Array (from 12 bytes)");
        standSize = new FactoryStandSize(() -> new int[0]);
        sizeObject = standSize.getSizeObject();
        System.out.println("Final size object: " + sizeObject);


        System.out.println("Size long (8 bytes)");
        standSize = new FactoryStandSize(() -> new long[]{10000L, 10001L});
        long sizeObject0 = standSize.getSizeObject();
        standSize = new FactoryStandSize(() -> new long[]{10001L, 10002L, 10003L});
        long sizeObject1 = standSize.getSizeObject();
        System.out.println("Final size object: " + (sizeObject1 - sizeObject0));

    }
}
