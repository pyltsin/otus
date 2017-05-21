package ru.otus.pyltsin.HW6;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Pyltsin on 21.05.2017. Algo8
 */
public class ATMDepartmentTest {
    private ATMDepartment atmDepartment;
    private int sum = 3 * 8 + 10 * 7 + 20 * 6 + 6 * 8 + 10 * 7 + 20 * 6;

    @Before
    public void setUp() throws Exception {
        atmDepartment = new ATMDepartment();
        atmDepartment.addATM(new ATM("/testOne.txt"));
        atmDepartment.addATM(new ATM("/testTwo.txt"));
    }


    @Test
    public void reInit() throws Exception {
        for (ATM atm : atmDepartment) {
            atm.getMoney(10);
        }
        assertNotEquals(sum, atmDepartment.getBalance());
        atmDepartment.reInit();
        assertEquals(sum, atmDepartment.getBalance());

    }

    @Test
    public void getBalance() throws Exception {
        assertEquals(sum, atmDepartment.getBalance());
    }

}