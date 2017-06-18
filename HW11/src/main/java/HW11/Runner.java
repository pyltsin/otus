package HW11;

import HW11.common.AddressDataSet;
import HW11.common.PhoneDataSet;
import HW11.common.UserDataSet;
import HW11.myCache.CacheEngine;
import HW11.myCache.CacheEngineImpl;
import HW11.service.DBService;
import HW11.service.HibernateDBService;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pyltsin on 11.06.2017.
 */
public class Runner {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());


        System.out.println("Check my Cache");
        //check Cache

        CacheEngine<Long, UserDataSet> cacheEngine = new CacheEngineImpl<>(2, 1_000, false);
        DBService dbService = new HibernateDBService(cacheEngine);

        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);

        Set<PhoneDataSet> list1 = new HashSet<>();
        list1.add(new PhoneDataSet(12, "12345"));
        list1.add(new PhoneDataSet(22, "22345"));

        printCacheParam(cacheEngine);

        dbService.save(new UserDataSet("slon", 12, new AddressDataSet("lik", 12),
                list1));


        UserDataSet userDataSet = (UserDataSet) dbService.read(1);
        printCacheParam(cacheEngine);
        userDataSet = (UserDataSet) dbService.read(1);
        printCacheParam(cacheEngine);
        Thread.currentThread().sleep(1_500);
        userDataSet = (UserDataSet) dbService.read(1);
        printCacheParam(cacheEngine);
        dbService.shutdown();
    }

    private static void printCacheParam(CacheEngine<Long, UserDataSet> cacheEngine) {
        System.out.println("Cache hit: " + cacheEngine.getHitCount());
        System.out.println("Cache miss: " + cacheEngine.getMissCount());
    }
}
