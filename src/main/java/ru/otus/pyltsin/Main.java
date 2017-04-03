package ru.otus.pyltsin;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pyltsin on 02.04.2017. Algo8
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //try count PI
        double pi = new Pi().getPi(100);
        System.out.println(pi);
        //test version JDK
        List<String> testList = new ArrayList<>();

        //add csv for example
        CSVReader reader = new CSVReader(new FileReader("test"));

    }
}
