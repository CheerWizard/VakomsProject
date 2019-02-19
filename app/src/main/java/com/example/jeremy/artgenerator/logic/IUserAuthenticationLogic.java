package com.example.jeremy.artgenerator.logic;

import com.example.jeremy.artgenerator.business_logic.data.User;
import com.example.jeremy.artgenerator.business_logic.repositories.IUserRepository;

import androidx.lifecycle.LiveData;

public interface IUserAuthenticationLogic extends IUserRepository {
    void login(String email);
    void register(User user);
    void restorePassword(String email);
}
