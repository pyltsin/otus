package ru.otus.pyltsin.HW4;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Pyltsin on 22.04.2017
 */

/*
Запуски через starter
 */
public class Runner {
    private static final int ADD = 300_000;
    private static final int DELETE = 150_000;
    private static final int DELETE_OLD = 10;

    public static void main(String... args) throws InterruptedException {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

        installGCMonitoring();

        Thread.sleep(10 * 1000);

        getOutOfMemory();
    }

    private static void getOutOfMemory() throws InterruptedException {
        System.out.println("Starting the loop");
        List<String> list = new ArrayList<>();
        while (true) {
            for (int i = 0; i < ADD; i++) {
                list.add(new String("")); //no String pool
            }
            for (int i = 0; i < DELETE; i++) {
                list.remove(list.size() - 1); //create YOUNG object
            }

            for (int i = 0; i < DELETE_OLD; i++) {
                list.remove(list.size() - 1); //create OLD object
            }


            System.out.println("Size list: " + list.size());
            Thread.sleep(1000);

        }
    }

    /**
     * Честно украдено с предыдущих уроков и немного модифицировано
     */
    private static void installGCMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        HashMap<String, Long> mapDur = new HashMap<>();
        long startTime = System.nanoTime();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            System.out.println(gcbean.getName());
            mapDur.put(gcbean.getName(), new Long(0));

            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

                    long duration = info.getGcInfo().getDuration();
                    mapDur.put(info.getGcName(), mapDur.get(info.getGcName()) + duration);
                    String gctype = info.getGcAction();

                    System.out.println(gctype + ": - "
                            + info.getGcInfo().getId() + ", "
                            + info.getGcName()
                            + " (from " + info.getGcCause() + ") " + duration + " milliseconds");
                    System.out.println(info.getGcName() +
                            ", sum duration : " + mapDur.get(info.getGcName()) + " ms" +
                            ", sum count: " + info.getGcInfo().getId() +
                            ", time: " + (System.nanoTime() - startTime) / 1000. / 1000 / 1000 / 60 + " min");
                    System.out.println("Free memory " + Runtime.getRuntime().freeMemory() );

                }
            };

            emitter.addNotificationListener(listener, null, null);
        }
    }

}
