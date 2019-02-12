package com.example.jeremy.artgenerator.business_logic.webservices;

import com.example.jeremy.artgenerator.business_logic.data.User;

import java.util.List;

public interface IUserWebService {
    List<User> getAll();
    void removeAll();
    void update(User user);
    void insert(User user);
    void remove(int position);
    void remove(String email);
    User get(int position);
    User get(String email , String password);
}
