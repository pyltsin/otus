package ru.otus.pyltsin.HW15.helper;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Pyltsin on 04.06.2017.
 */
@SuppressWarnings("Duplicates")
public class PropertiesHelper {

    public static Properties getProperties(String file) throws IOException {
        Properties properties = new Properties();
        properties.load(PropertiesHelper.class.getClassLoader().getResourceAsStream(file));
        return properties;
    }
}
