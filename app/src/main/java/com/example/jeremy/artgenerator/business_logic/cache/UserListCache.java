package com.example.jeremy.artgenerator.business_logic.cache;

import com.example.jeremy.artgenerator.business_logic.data.User;

import java.util.List;

import androidx.lifecycle.LiveData;

public class UserListCache {

    private LiveData<List<User>> userListLiveData;

    public UserListCache() {
    }

    public LiveData<List<User>> getUserListLiveData() {
        return userListLiveData;
    }

    public void setUserListLiveData(LiveData<List<User>> userListLiveData) {
        this.userListLiveData = userListLiveData;
    }
}
