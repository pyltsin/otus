package ru.otus.pyltsin.HW9.common;

import javax.persistence.*;

/**
 * Created by Pyltsin on 04.06.2017. Algo8
 */
@Entity
@Table(name = "USERS")
public class User {
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
