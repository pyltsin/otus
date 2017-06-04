package ru.otus.pyltsin.HW9.Helper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Pyltsin on 04.06.2017. Algo8
 */
public class ConnectionHelper {

    private static Properties getProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(ConnectionHelper.class.getClassLoader().getResourceAsStream("mysql.properties"));
        return properties;
    }

    public static Connection getConnection()  {

        try {
            Properties properties = getProperties();
            String url = properties.getProperty("url");
            String driver = properties.getProperty("driver");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");

            Class.forName(driver);

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test() {

        try (Connection connection = getConnection()) {
            System.out.println("Connected to: " + connection.getMetaData().getURL());
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
