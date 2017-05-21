package ru.otus.pyltsin.HW6;

/**
 * Created by Pyltsin on 21.05.2017. Algo8
 */
public enum AtmType {
    ONE("/properties/one.txt"),
    TWO("/properties/two.txt");

    private final String path;

    AtmType(String s) {
        path = s;
    }

    public String getPath() {
        return path;
    }
}
