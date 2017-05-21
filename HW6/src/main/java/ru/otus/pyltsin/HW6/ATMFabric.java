package ru.otus.pyltsin.HW6; /**
 * Created by Pyltsin on 21.05.2017. Algo8
 */

/**
 * Simple fabric for object;
 */
public class ATMFabric {
    public static ATM getATM(AtmType atmType) {
        return new ATM(atmType.getPath());
    }
}
