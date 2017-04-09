package ru.otus.pyltsin.HW1_1;

/**
 * Created by tully.
 */
public class Pi {


    public double getPi(int tol) {
        int c = 0;
        int out = 0;
        int r2 = tol * tol;
        int max = tol * 2;
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                if (Math.pow(i - tol, 2) + Math.pow(j - tol, 2) < r2) {
                    c++;
                }
                out++;
            }
        }
        return c * 1. / out * 4;
    }
}
