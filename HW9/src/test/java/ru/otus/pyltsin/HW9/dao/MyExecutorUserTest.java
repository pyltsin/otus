package ru.otus.pyltsin.HW9.dao;

import org.junit.Before;
import org.junit.Test;
import ru.otus.pyltsin.HW9.Helper.ConnectionHelper;
import ru.otus.pyltsin.HW9.Helper.ScriptRunner;
import ru.otus.pyltsin.HW9.Runner;
import ru.otus.pyltsin.HW9.common.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pyltsin on 04.06.2017. Algo8
 */
public class MyExecutorUserTest {
    @Before
    public void setUp() throws Exception {
        Connection connection = ConnectionHelper.getConnection();
        ScriptRunner runner = new ScriptRunner(connection, false, true);
        InputStreamReader inputStreamReader1 = new InputStreamReader(Runner.class.getClassLoader().getResourceAsStream("create_schema.sql"));
        InputStreamReader inputStreamReader2 = new InputStreamReader(Runner.class.getClassLoader().getResourceAsStream("generate_content.sql"));
        runner.runScript(new BufferedReader(inputStreamReader1));
        runner.runScript(new BufferedReader(inputStreamReader2));
        connection.close();
    }

    @Test
    public void save() throws Exception {
        ExecutorUser executorUser = new MyExecutorUser();
        User user = new User();
        user.setName("Slon");
        user.setAge(25);
        executorUser.save(user);
        assertEquals(user.toString(), executorUser.load(4, User.class).toString());
    }

    @Test
    public void load() throws Exception {
        ExecutorUser executorUser = new MyExecutorUser();
        User user = executorUser.load(1, User.class);
        assertEquals(1, user.getId());
        assertEquals("vasya", user.getName());
        assertEquals(23, user.getAge());
    }
}