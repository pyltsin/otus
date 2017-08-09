package ru.otus.pyltsin.HW16.app;

import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.TypeAddress;

/**
 * @author tully
 */
public interface Addressee {
    Address getAddress();

    void init();
}
