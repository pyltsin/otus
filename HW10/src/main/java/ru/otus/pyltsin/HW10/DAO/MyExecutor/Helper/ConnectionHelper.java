package ru.otus.pyltsin.HW10.DAO.MyExecutor.Helper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Pyltsin on 04.06.2017. Algo8
 */
@SuppressWarnings("Duplicates")
public class ConnectionHelper {

    public static Properties getProperties(String file) throws IOException {
        Properties properties = new Properties();
        properties.load(ConnectionHelper.class.getClassLoader().getResourceAsStream(file));
        return properties;
    }

    public static Connection getConnection()  {

        try {
            Properties properties = getProperties("mysql.properties");
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

    public static String test() {
        StringBuilder stringBuilder = new StringBuilder();
        try (Connection connection = getConnection()) {
            stringBuilder.append("Connected to: " + connection.getMetaData().getURL());
            stringBuilder.append("DB name: " + connection.getMetaData().getDatabaseProductName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
