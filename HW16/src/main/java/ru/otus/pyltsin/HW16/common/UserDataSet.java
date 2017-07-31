package ru.otus.pyltsin.HW16.common;

import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Pyltsin on 04.06.2017. Algo8
 */
@Entity
@Table(name = "USERS2")
@Proxy(lazy = false)
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String nameUser;
    @Column(name = "age")
    private int ageUser;

    public UserDataSet(String nameUser, int ageUser) {
        this.nameUser = nameUser;
        this.ageUser = ageUser;
    }


    public UserDataSet() {
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "nameUser='" + nameUser + '\'' +
                ", ageUser=" + ageUser +
                '}';
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public int getAgeUser() {
        return ageUser;
    }

    public void setAgeUser(int ageUser) {
        this.ageUser = ageUser;
    }

    public String getName() {
        return nameUser;
    }

    public void setName(String name) {
        this.nameUser = name;
    }

    public int getAge() {
        return ageUser;
    }

    public void setAge(int age) {
        this.ageUser = age;
    }
}
