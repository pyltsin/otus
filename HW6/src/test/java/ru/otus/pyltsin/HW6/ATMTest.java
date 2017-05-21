package ru.otus.pyltsin.HW6;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pyltsin on 21.05.2017. Algo8
 */
public class ATMTest {
    private ATM atm;

    @Before
    public void setUp() throws Exception {
        atm = new ATM("/testOne.txt");
    }


    @Test
    public void init() throws Exception {
        assertEquals(24 + 70 + 120, atm.getBalance());
    }


    @Test
    public void reInit() throws Exception {
        atm.reInit("/testTwo.txt");
        assertEquals(6 * 8 + 70 + 120, atm.getBalance());
    }

    @Test
    public void setMoney() throws Exception {
        atm.setMoney(new Cell(3, 4));
        assertEquals(3 * 12 + 70 + 120, atm.getBalance());

    }

    @Test
    public void getMoneyUgly() throws Exception {
        getCash(20);
    }

    public void getCash(int cash) throws ATMException {
        int bal = atm.getBalance();
        List<Cell> out = atm.getMoney(cash);
        assertEquals(cash, -atm.getBalance() + bal);
        int sum = 0;

        for (Cell cell : out) {
            System.out.println(cell);
            sum += cell.getBalance();
        }
        assertEquals(cash, sum);
    }

    @Test
    public void getMoneyAll() throws Exception {
        getCash(12);
    }
}