package ru.otus.pyltsin.HW9.common;

import javax.persistence.*;

/**
 * Created by Pyltsin on 04.06.2017. Algo8
 */
@Entity
@Table(name = "USERS")
public class User implements DataSet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;

    @Column(name = "name")
    private String nameUser;
    @Column(name = "age")
    private int ageUser;

    public User() {
    }

    public long getId() {
        return idUser;
    }

    public String getName() {
        return nameUser;
    }

    public int getAge() {
        return ageUser;
    }

    public void setName(String name) {
        this.nameUser = name;
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
