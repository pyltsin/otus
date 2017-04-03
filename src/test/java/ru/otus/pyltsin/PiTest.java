package ru.otus.pyltsin;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Pyltsin on 01.04.2017. Algo8
 */
public class PiTest {
    @Test
    public void testGetPi() throws Exception {

        assertEquals("TestPI", 3.1415, new Pi().getPi(100), 0.01);
    }

}