package ru.otus.pyltsin.HW6;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pyltsin on 21.05.2017. Algo8
 */
public class CellTest {
    @Test
    public void getBalance() throws Exception {
        Cell cell = new Cell(10, 5);
        assertEquals(50, cell.getBalance());
    }

    @Test
    public void compareTo() throws Exception {
        Cell cell1 = new Cell(15, 5);
        Cell cell2 = new Cell(10, 7);
        assertTrue(cell1.compareTo(cell2) > 0);
    }

    @Test
    public void add() throws Exception {
        Cell cell = new Cell(10, 5);
        cell.add(7);
        assertEquals(12, cell.getCount());
    }


    @Test
    public void getNominal() throws Exception {
        Cell cell = new Cell(10, 5);
        assertEquals(10, cell.getNominal());
    }

    @Test
    public void remove() throws Exception {
        Cell cell = new Cell(10, 5);
        try {
            cell.remove(3);
        } catch (ATMException e) {
            e.printStackTrace();
        }
        assertEquals(2, cell.getCount());

    }

}