package ru.otus.pyltsin.HW16.app;

import ru.otus.pyltsin.HW16.messageSystem.Address;

/**
 * @author tully
 */
public interface Addressee {
    Address getAddress();

    void init();
}
