package ru.otus.pyltsin.HW3.MyCollection;

import java.util.*;

/**
 * Created by Pyltsin on 16.04.2017.
 * <p>
 * Concurrent Exception not implements.
 * Simple ArrayList.
 * Implement grow array.
 */
public class MyArrayList<T> implements List<T> {
    @SuppressWarnings("FieldCanBeLocal")
    private static final int INITIATION_SIZE = 24;
    @SuppressWarnings("FieldCanBeLocal")
    private static final double THRESHOLD = 0.75;
    @SuppressWarnings("FieldCanBeLocal")
    private static final double MULTIPLY_SIZE = 1.5;
    private int size = 0;

    private Object[] array;

    public MyArrayList() {
        array = new Object[INITIATION_SIZE];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            Object o1 = array[i];
            if (o1.equals(o)) {
                return true;
            }
        }

        return false;
    }


    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }


    @Override
    public boolean add(T t) {
        checkSize();
        array[size] = t;
        size++;
        return true;
    }

    private void checkSize() {
        if (size >= THRESHOLD * array.length) {
            resizeArray();
        }
    }

    private void resizeArray() {
        array = Arrays.copyOf(array, (int) (MULTIPLY_SIZE * array.length));
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < array.length; i++) {
            Object o1 = array[i];
            if (o1.equals(o)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void add(int index, T element) {
        checkIndex(index);
        checkSize();
        System.arraycopy(array, index, array, index + 1,
                size - index);
        array[index] = element;
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }


    @Override
    public T remove(int index) {
        checkIndex(index);

        T out = getUncheckedEl(index);
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(array, index + 1, array, index,
                    numMoved);
        array[--size] = null;
        return out;
    }

    @SuppressWarnings("unchecked")
    private T getUncheckedEl(int index) {
        return (T) array[index];
    }


    @Override
    public T get(int index) {
        if (index > size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return getUncheckedEl(index);
    }

    @Override
    public T set(int index, T element) {
        if (index > size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        array[index] = element;
        return element;
    }


    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyListItr();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }


    //UNSUPPORTED OPERATION!


    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private class MyIterator implements Iterator<T> {

        int current = 0;

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public T next() {
            T out = getUncheckedEl(current);
            current++;
            return out;
        }


        @Override
        public void remove() {
            throw new UnsupportedOperationException();

        }

    }

    private class MyListItr extends MyIterator implements ListIterator<T> {

        @Override
        public void set(T t) {
            MyArrayList.this.set(current - 1, t);
        }


        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException();
        }

        @Override
        public T previous() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException();

        }
    }


}
