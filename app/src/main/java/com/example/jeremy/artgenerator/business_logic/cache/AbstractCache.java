package com.example.jeremy.artgenerator.business_logic.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;

public abstract class AbstractCache<K,V> {

    protected Map<K,LiveData<V>> cache;

    protected AbstractCache() {
        cache = new HashMap<>();
    }

    public LiveData<V> get(K k) {
        return cache.get(k);
    }

    public void remove(K k) {
        cache.remove(k);
    }

    public void removeAll() {
        cache.clear();
    }

    public List<LiveData<V>> getAll() {
        return new ArrayList<>(cache.values());
    }

    public boolean hasKey(K k) {
        return cache.containsKey(k);
    }

    public abstract void add(LiveData<V> v);
    public abstract void addAll(List<LiveData<V>> values);
}
