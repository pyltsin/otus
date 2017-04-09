package ru.otus.pyltsin.HW2.StandSize;

/**
 * Created by Pyltsin on 09.04.2017. Algo8
 */

import ru.otus.pyltsin.HW2.StandSize.Interfaces.StandSize;

/**
 * AbstractStandSize for dimension object
 */
abstract public class AbstractStandSize implements StandSize {

    private int sizeArray = 1_000_000;
    private int countIter = 10;

    public int getCountIter() {
        return countIter;
    }

    public void setCountIter(int countIter) {
        this.countIter = countIter;
    }

    public int getSizeArray() {
        return sizeArray;
    }

    public void setSizeArray(int sizeArray) {
        this.sizeArray = sizeArray;
    }

    /**
     * Main method
     *
     * @return size object in byte
     */
    public long getSizeObject() {
        long sizeIterSum = 0;
        for (int i = 0; i < countIter; i++) {
            sizeIterSum += getSizeIter();
        }
        return sizeIterSum / countIter;
    }


    private long getSizeIter() {
        Object[] array = new Object[sizeArray];
        int i;
        long size;
        long sizeEnd;

        Runtime.getRuntime().gc();
        size = getSizeMemory();

        for (i = 0; i < sizeArray; i++) {
            array[i] = getObject();
        }

        sizeEnd = getSizeMemory();

        array = null;

        Runtime.getRuntime().gc();

        return (sizeEnd - size) / sizeArray;
    }

    protected abstract Object getObject();


    /**
     * @return size heap in byte
     */
    private long getSizeMemory() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }
}
