package com.example.jeremy.artgenerator.business_logic.cache;

import com.example.jeremy.artgenerator.business_logic.data.Sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;

public class SampleCache implements ICache<Integer , Sample> {

    private Map<Integer , LiveData<Sample>> cache;

    public SampleCache() {
        cache = new HashMap<>();
    }

    @Override
    public void add(LiveData<Sample> sampleLiveData) {
        cache.put(sampleLiveData.getValue().getId() , sampleLiveData);
    }

    @Override
    public LiveData<Sample> get(Integer integer) {
        return cache.get(integer);
    }

    @Override
    public void remove(Integer integer) {
        cache.remove(integer);
    }

    @Override
    public void removeAll() {
        cache.clear();
    }

    @Override
    public List<LiveData<Sample>> getAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public void addAll(List<LiveData<Sample>> values) {
        for (LiveData<Sample> userLiveData : values) cache.put(userLiveData.getValue().getId() , userLiveData);
    }

    public boolean hasKey(Integer key) {
        return cache.containsKey(key);
    }
}
