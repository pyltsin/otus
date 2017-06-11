package ru.otus.pyltsin.HW10;

import org.apache.ibatis.jdbc.ScriptRunner;
import ru.otus.pyltsin.HW10.DAO.MyExecutor.Helper.ConnectionHelper;
import ru.otus.pyltsin.HW10.common.AddressDataSet;
import ru.otus.pyltsin.HW10.common.PhoneDataSet;
import ru.otus.pyltsin.HW10.common.SimpleUser;
import ru.otus.pyltsin.HW10.common.UserDataSet;
import ru.otus.pyltsin.HW10.service.DBService;
import ru.otus.pyltsin.HW10.service.DBServiceHibUserDataSet;
import ru.otus.pyltsin.HW10.service.MyDBServiceSimpleUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Pyltsin on 11.06.2017.
 */
public class Runner {
    public static void main(String[] args) throws IOException {
        //test connect for myImpl

        Connection connection = ConnectionHelper.getConnection();
        ConnectionHelper.test();

        // reset database for myImpl
        ScriptRunner runner = new ScriptRunner(connection);
        InputStreamReader inputStreamReader1 = new InputStreamReader(Runner.class.getClassLoader().getResourceAsStream("create_schema.sql"));
        InputStreamReader inputStreamReader2 = new InputStreamReader(Runner.class.getClassLoader().getResourceAsStream("generate_content.sql"));
        runner.runScript(new BufferedReader(inputStreamReader1));
        runner.runScript(new BufferedReader(inputStreamReader2));


        //myImpl DBService

        SimpleUser user = new SimpleUser();
        user.setName("Slon");
        user.setAge(25);

        DBService myDbService = new MyDBServiceSimpleUser();

        myDbService.save(user);

        SimpleUser simpleUser = (SimpleUser) myDbService.read(4);
        System.out.println(simpleUser);
        System.out.println(myDbService.read(1));

        SimpleUser simpleUser2 = (SimpleUser) myDbService.readByName("vasya");
        System.out.println(simpleUser2);


        //Hibernate

        DBService dbService = new DBServiceHibUserDataSet();
        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);

        Set<PhoneDataSet> list1 = new HashSet<>();
        list1.add(new PhoneDataSet(12, "12345"));
        list1.add(new PhoneDataSet(22, "22345"));

        Set<PhoneDataSet> list2 = new HashSet<>();
        list2.add(new PhoneDataSet(122, "122345"));
        list2.add(new PhoneDataSet(222, "222345"));
        dbService.save(new UserDataSet("slon", 12, new AddressDataSet("lik", 12),
                list1));
        dbService.save(new UserDataSet("bear", 122, new AddressDataSet("lik2", 12),
                list2));
        UserDataSet dataSet = (UserDataSet) dbService.read(1);
        System.out.println(dataSet);

        dataSet = (UserDataSet) dbService.readByName("bear");
        System.out.println(dataSet);

        List<UserDataSet> dataSets = (List<UserDataSet>) dbService.readAll();
        for (UserDataSet userDataSet : dataSets) {
            System.out.println(userDataSet);
        }


        dbService.shutdown();

    }
}
