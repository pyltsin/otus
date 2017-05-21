package ru.otus.pyltsin.HW6;

/**
 * Created by Pyltsin on 21.05.2017. Algo8
 */
public class Cell implements Comparable<Cell>, GetBalancable {
    private int count;
    private final int nominal;

    public Cell(int nominal, int count) {
        this.count = count;
        this.nominal = nominal;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "count=" + count +
                ", nominal=" + nominal +
                '}';
    }

    public int getBalance() {
        return count * nominal;
    }


    @Override
    public int compareTo(Cell o) {
        return nominal - o.nominal;
    }


    public void add(int count) {
        this.count += count;
    }

    public int getCount() {
        return count;
    }

    public int getNominal() {
        return nominal;
    }

    public void remove(int count) throws ATMException {
        if (count > this.count) {
            throw new ATMException("Cell not contain enough money");
        }
        this.count -= count;

    }
}
