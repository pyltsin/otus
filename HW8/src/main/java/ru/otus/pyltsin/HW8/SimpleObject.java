package ru.otus.pyltsin.HW8;

import java.util.List;
import java.util.Map;

/**
 * Created by Pyltsin on 27.05.2017.
 */
public class SimpleObject {
    private int i;
    private String string;
    private boolean bool;
    private Double aDouble;
    private double[] doubles;
    private List<Integer> list;
    private Map<String, Double> map;
    private transient String nonPrint;

    public Double getADouble() {
        return aDouble;
    }

    public void setADouble(Double aDouble) {
        this.aDouble = aDouble;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public void setDoubles(double[] doubles) {
        this.doubles = doubles;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public void setMap(Map<String, Double> map) {
        this.map = map;
    }

    public void setNonPrint(String nonPrint) {
        this.nonPrint = nonPrint;
    }
}
