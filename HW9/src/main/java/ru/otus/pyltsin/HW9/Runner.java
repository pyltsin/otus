package ru.otus.pyltsin.HW9;

import org.apache.ibatis.jdbc.ScriptRunner;
import ru.otus.pyltsin.HW9.Helper.ConnectionHelper;
import ru.otus.pyltsin.HW9.common.User;
import ru.otus.pyltsin.HW9.dao.ExecutorUser;
import ru.otus.pyltsin.HW9.dao.MyExecutorUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Pyltsin on 04.06.2017. Algo8
 */
public class Runner {
    public static void main(String[] args) throws IOException, SQLException {
        //test connect

        Connection connection = ConnectionHelper.getConnection();
        ConnectionHelper.test();

        // reset database
        ScriptRunner runner = new ScriptRunner(connection);
        InputStreamReader inputStreamReader1 = new InputStreamReader(Runner.class.getClassLoader().getResourceAsStream("create_schema.sql"));
        InputStreamReader inputStreamReader2 = new InputStreamReader(Runner.class.getClassLoader().getResourceAsStream("generate_content.sql"));
        runner.runScript(new BufferedReader(inputStreamReader1));
        runner.runScript(new BufferedReader(inputStreamReader2));

        //myImpl

        User user = new User();
        user.setName("Slon");
        user.setAge(25);

        ExecutorUser executorUser = new MyExecutorUser();
        User userOut = executorUser.load(1, User.class);
        System.out.println(userOut);

        executorUser.save(user);

    }
}
