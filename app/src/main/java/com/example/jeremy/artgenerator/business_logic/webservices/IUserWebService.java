package com.example.jeremy.artgenerator.business_logic.webservices;

import com.example.jeremy.artgenerator.business_logic.data.User;

import java.util.List;

public interface IUserWebService extends IRetrofitWebService<User> {
    User get(String email);
}
