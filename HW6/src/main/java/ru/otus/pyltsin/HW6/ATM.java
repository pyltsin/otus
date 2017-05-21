package ru.otus.pyltsin.HW6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Pyltsin on 21.05.2017. Algo8
 */
public class ATM implements GetBalancable, Reinitable {
    private String prop;
    private List<Cell> cells;

    public ATM(String prop) {
        this.prop = prop;
        reInit(null);
    }

    public void reInit(String propNew) {
        if (propNew != null && !propNew.equals("")) {
            prop = propNew;
        }
        cells = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(this.getClass().getResourceAsStream(prop)))) {
            while (bufferedReader.ready()) {
                String[] num = bufferedReader.readLine().split(" ");

                Cell cell = new Cell(Integer.parseInt(num[0]), Integer.parseInt(num[1]));
                cells.add(cell);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(cells);
    }

    public void setMoney(Cell cash) throws ATMException {
        for (Cell cell : cells) {
            if (cash.compareTo(cell) == 0) {
                cell.add(cash.getCount());
                return;
            }
        }
        throw new ATMException("Not contain this cell");
    }


    public List<Cell> getMoney(int cash) throws ATMException {
        int balance = getBalance();
        if (cash > balance) {
            throw new ATMException("too much");
        }
        try {
            return uglyGetMoney(cash);
        } catch (ATMException e) {
            return allGetMoney(cash);
        }
    }

    private List<Cell> allGetMoney(int cash) throws ATMException {
        int ost = getBalance() - cash;
        List<Cell> ostCell = uglyGetMoney(ost);
        List<Cell> out = cells;
        cells = ostCell;
        return out;
    }

    private List<Cell> uglyGetMoney(int cash) throws ATMException {
        List<Cell> cellsOut = new ArrayList<>();
        for (int i = cells.size() - 1; i >= 0; i--) {
            Cell currentCell = cells.get(i);
            int num = cash / currentCell.getNominal();
            if (num > currentCell.getCount()) {
                num = currentCell.getCount();
            }
            Cell out = new Cell(currentCell.getNominal(), num);
            cash -= out.getBalance();
            cellsOut.add(out);
        }
        if (cash == 0) {
            Collections.sort(cellsOut);
            for (int i = 0; i < cellsOut.size(); i++) {
                cells.get(i).remove(cellsOut.get(i).getCount());
            }
            return cellsOut;
        } else {
            throw new ATMException("Not enough money");
        }
    }

    public int getBalance() {
        int out = 0;
        for (Cell cell : cells) {
            out += cell.getBalance();
        }
        return out;
    }


    @Override
    public void reInit() {
        this.reInit(null);
    }
}
