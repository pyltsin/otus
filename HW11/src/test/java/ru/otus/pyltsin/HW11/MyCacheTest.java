package ru.otus.pyltsin.HW11;

import HW11.Runner;
import HW11.common.AddressDataSet;
import HW11.common.PhoneDataSet;
import HW11.common.UserDataSet;
import HW11.myCache.CacheEngine;
import HW11.myCache.CacheEngineImpl;
import HW11.service.DBService;
import HW11.service.Helper.ConnectionHelper;
import HW11.service.HibernateDBService;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pyltsin on 04.06.2017.
 */
public class MyCacheTest {
    private DBService dbService;
    private CacheEngine<Long, UserDataSet> cacheEngine;
    @Before
    public void setUp() throws Exception {
        //set cache
        cacheEngine = new CacheEngineImpl<>(2, 1_000);
        dbService = new HibernateDBService(cacheEngine);
    }

    public void setBd() throws SQLException {
        Connection connection = ConnectionHelper.getConnection();
        ScriptRunner runner = new ScriptRunner(connection);
        InputStreamReader inputStreamReader2 = new InputStreamReader(Runner.class.getClassLoader().getResourceAsStream("generate_content.sql"));
        runner.runScript(new BufferedReader(inputStreamReader2));
        connection.close();
    }

    @Test
    public void miss() throws Exception {
        setBd();
        dbService.read(1);
        assertEquals(0, cacheEngine.getHitCount());
        assertEquals(1, cacheEngine.getMissCount());
    }

    @Test
    public void idle() throws Exception {
        setBd();
        dbService.read(1);
        Thread.currentThread().sleep(1_500);
        dbService.read(1);

        assertEquals(0, cacheEngine.getHitCount());
        assertEquals(2, cacheEngine.getMissCount());
    }

    @Test
    public void max() throws Exception {
        setBd();
        dbService.read(1);
        dbService.read(2);
        dbService.read(3);

        dbService.read(1);

        assertEquals(0, cacheEngine.getHitCount());
        assertEquals(4, cacheEngine.getMissCount());

    }

    @Test
    public void hintForSave() throws Exception {
        Set<PhoneDataSet> list1 = new HashSet<>();
        list1.add(new PhoneDataSet(12, "12345"));
        list1.add(new PhoneDataSet(22, "22345"));
        UserDataSet userDataSet = (UserDataSet) dbService.save(new UserDataSet("slon", 12, new AddressDataSet("lik", 12),
                list1));
        dbService.read(userDataSet.getId());
        assertEquals(1, cacheEngine.getHitCount());
        assertEquals(0, cacheEngine.getMissCount());
    }

    @Test
    public void hintForRead() throws Exception {
        setBd();

        dbService.read(1);
        dbService.read(1);
        assertEquals(1, cacheEngine.getHitCount());
        assertEquals(1, cacheEngine.getMissCount());

    }
}