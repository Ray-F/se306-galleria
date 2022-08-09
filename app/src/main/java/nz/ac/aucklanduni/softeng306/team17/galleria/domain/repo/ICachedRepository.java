package nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo;


import java.util.HashMap;
import java.util.Map;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.DomainModel;

abstract class ICachedRepository<T extends DomainModel> implements IRepository<T> {

    Map<String, T> cache = new HashMap<>();

    /**
     * Returns the cached value associated with key.
     */
    public T get(String id) {
        T cached = cache.getOrDefault(id, null);

        if (cached == null) {
            cached = getFromPersistence(id);
            cache.put(id, cached);
        }

        return cached;
    }

    /**
     * Method stub for overriding.
     */

    protected abstract T getFromPersistence(String id);

}
