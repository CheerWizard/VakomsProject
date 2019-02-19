package com.example.jeremy.artgenerator.business_logic.cache;

import com.example.jeremy.artgenerator.business_logic.data.User;
import com.example.jeremy.artgenerator.utils.resolvers.ObjectResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;

public class UserCache extends AbstractCache<String , User> {

    public UserCache() {
        super();
    }

    @Override
    public void add(LiveData<User> userLiveData) {
        cache.put(userLiveData.getValue().getEmail() , userLiveData);
    }

    @Override
    public void addAll(List<LiveData<User>> values) {
        for (LiveData<User> userLiveData : values) cache.put(userLiveData.getValue().getEmail() , userLiveData);
    }

    @Override
    public LiveData<User> get(String s) {
        return super.get(s);
    }

    @Override
    public void remove(String s) {
        super.remove(s);
    }

    @Override
    public void removeAll() {
        super.removeAll();
    }

    @Override
    public List<LiveData<User>> getAll() {
        return super.getAll();
    }

    @Override
    public boolean hasKey(String s) {
        return super.hasKey(s);
    }
}
