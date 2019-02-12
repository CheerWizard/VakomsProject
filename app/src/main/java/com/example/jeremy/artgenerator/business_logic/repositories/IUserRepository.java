package com.example.jeremy.artgenerator.business_logic.repositories;

import com.example.jeremy.artgenerator.business_logic.data.User;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface IUserRepository {
    LiveData<List<User>> getUsers();
    LiveData<User> getUser(String email , String password);
    void update(User user);
    void insert(User user);
    void close();
}
