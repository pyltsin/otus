package HW11.myCache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tully.
 * Update by pyltsin
 */

public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {
    private static final int TIME_THRESHOLD_MS = 5;

    private final int maxElements;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, CacheElement<K, V>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    public CacheEngineImpl(int maxElements, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = idleTimeMs == 0 || isEternal;
    }

    public void put(CacheElement<K, V> element) {
        if (elements.containsKey(element.getKey())) {
            return;
        }

        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        K key = element.getKey();
        elements.put(key, element);

        if (!isEternal) {
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key);
                timer.schedule(idleTimerTask, idleTimeMs);
            }
        }
    }

    public CacheElement<K, V> get(K key) {
        CacheElement<K, V> element = elements.get(key);
        if (element != null) {
            hit++;
            element.setAccessed();
        } else {
            miss++;
        }
        return element;
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key) {
        return new TimerTask() {
            @Override
            public void run() {
                CacheElement<K, V> checkedElement = elements.get(key);
                if (checkedElement == null ||
                        isT1BeforeT2(checkedElement.getLastAccessTime() + idleTimeMs, System.currentTimeMillis())) {
                    elements.remove(key);
                }
            }
        };
    }


    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }
}
