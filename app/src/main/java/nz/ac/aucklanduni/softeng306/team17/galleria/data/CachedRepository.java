package nz.ac.aucklanduni.softeng306.team17.galleria.data;

import java.util.HashMap;
import java.util.Map;

public abstract class CachedRepository<T> {

    private Map<String, T> cache = new HashMap<>();

    protected T getFromCacheOrNull(String key) {
        return cache.getOrDefault(key, null);
    }


    protected void addToCache(String key, T obj) {
        cache.put(key, obj);
    }

}
