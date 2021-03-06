package ru.otus.pyltsin.HW13.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Pyltsin on 11.06.2017.
 */
@Entity
@Table(name = "PHONES")
public class PhoneDataSet extends DataSet {

    @Column(name = "CODE")
    private int code;
    @Column(name = "NUMBER")
    private String number;

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "code=" + code +
                ", number='" + number + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneDataSet(int code, String number) {
        this.code = code;
        this.number = number;
    }

    public PhoneDataSet() {

    }
}
