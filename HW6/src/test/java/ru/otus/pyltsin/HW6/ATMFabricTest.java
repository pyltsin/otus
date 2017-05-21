package ru.otus.pyltsin.HW6;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pyltsin on 21.05.2017. Algo8
 */
public class ATMFabricTest {
    @Test
    public void getATMOne() throws Exception {
        ATM atm = ATMFabric.getATM(AtmType.ONE);
        assertEquals(210, atm.getBalance());
    }

    @Test
    public void getATMTwo() throws Exception {
        ATM atm = ATMFabric.getATM(AtmType.TWO);
        assertEquals(190, atm.getBalance());
    }

}
