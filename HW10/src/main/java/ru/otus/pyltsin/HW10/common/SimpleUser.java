package ru.otus.pyltsin.HW10.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Pyltsin on 04.06.2017. Algo8
 */
@Entity
@Table(name = "USERS")
public class SimpleUser extends DataSet {

    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String nameUser;
    @Column(name = "age")
    private int ageUser;

    public SimpleUser() {
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

    @Override
    public String toString() {
        return "User{" +
                "name='" + nameUser + '\'' +
                ", age=" + ageUser +
                '}';
    }
}
