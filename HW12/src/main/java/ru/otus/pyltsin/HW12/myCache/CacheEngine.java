package ru.otus.pyltsin.HW12.myCache;

/**
 * Created by tully.
 * Update by pyltsin
 */

public interface CacheEngine<K, V> {

    void put(CacheElement<K, V> element);

    CacheElement<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();
}
