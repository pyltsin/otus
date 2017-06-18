package HW11.myCache;

/**
 * Created by tully.
 * Update by pyltsin
 */

public class CacheElement<K, V> {
    private final K key;
    private final V value;
    private long lastAccessTime;


    public CacheElement(K key, V value) {
        this.key = key;
        this.value = value;
        this.lastAccessTime = getCurrentTime();
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }
}
