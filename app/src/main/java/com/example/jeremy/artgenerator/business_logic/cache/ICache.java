package com.example.jeremy.artgenerator.business_logic.cache;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface ICache<K,V> {
    void add(LiveData<V> v);
    LiveData<V> get(K k);
    void remove(K k);
    void removeAll();
    List<LiveData<V>> getAll();
    void addAll(List<LiveData<V>> values);
}
