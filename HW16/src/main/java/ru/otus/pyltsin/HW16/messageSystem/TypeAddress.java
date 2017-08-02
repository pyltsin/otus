package ru.otus.pyltsin.HW16.messageSystem;

/**
 * Created by Pyltsin on 31.07.2017.
 */
public enum TypeAddress {
    DB, FRONTEND, MESSAGE_SERVER;

    public static boolean contains(String id) {
        for (TypeAddress typeAddress : TypeAddress.values()) {
            if (typeAddress.name().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
