package com.example.jeremy.artgenerator.business_logic.cache;

import com.example.jeremy.artgenerator.business_logic.data.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;

public class UserCache implements ICache<String , User> {

    private Map<String , LiveData<User>> cache;

    public UserCache() {
        cache = new HashMap<>();
    }

    @Override
    public void add(LiveData<User> user) {
        cache.put(user.getValue().getEmail() , user);
    }

    @Override
    public LiveData<User> get(String s) {
        return cache.get(s);
    }

    @Override
    public void remove(String s) {
        cache.remove(s);
    }

    @Override
    public void removeAll() {
        cache.clear();
    }

    @Override
    public List<LiveData<User>> getAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public void addAll(List<LiveData<User>> values) {
        for (LiveData<User> userLiveData : values) cache.put(userLiveData.getValue().getEmail() , userLiveData);
    }

    public boolean hasKey(Integer key) {
        return cache.containsKey(key);
    }

}
