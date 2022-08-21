package nz.ac.aucklanduni.softeng306.team17.galleria.data;

import java.util.HashMap;
import java.util.Map;

public abstract class CachedRepository<T> {

    private Map<String, T> cache = new HashMap<>();

    protected T getFromCacheOrNull(String key) {
        T cachedItem = cache.getOrDefault(key, null);

        if (cachedItem != null) {
            System.out.printf("Retrieving \"%s\" (type: %s) from cache.", key, cachedItem.getClass());
        }

        return cachedItem;
    }


    protected void addToCache(String key, T obj) {
        cache.put(key, obj);
    }

}
