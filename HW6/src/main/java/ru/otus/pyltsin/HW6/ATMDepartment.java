package ru.otus.pyltsin.HW6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Pyltsin on 21.05.2017. Algo8
 */
public class ATMDepartment implements GetBalancable, Reinitable, Iterable<ATM> {
    private List<ATM> atms;

    public ATMDepartment() {
        atms = new ArrayList<>();
    }

    public void addATM(ATM... atm) {
        Collections.addAll(atms, atm);
    }

    public void reInit() {
        for (ATM atm : atms) {
            atm.reInit(null);
        }
    }

    public int getBalance() {
        int out = 0;
        for (ATM atm : atms) {
            out += atm.getBalance();
        }
        return out;
    }

    @Override
    public Iterator<ATM> iterator() {
        return atms.iterator();
    }
}

